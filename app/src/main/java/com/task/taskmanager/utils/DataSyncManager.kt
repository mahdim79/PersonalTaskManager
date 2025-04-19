package com.task.taskmanager.utils

import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.AddMultipleTasks
import com.task.core.interactors.GetLocalTasks
import com.task.core.interactors.GetRemoteTasks
import com.task.core.interactors.RemoveRemoteTasks
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class DataSyncManager @Inject constructor() {

    @Inject
    lateinit var getRemoteTasks: GetRemoteTasks

    @Inject
    lateinit var getLocalTasks: GetLocalTasks

    @Inject
    lateinit var isOnline: IsOnline

    @Inject
    lateinit var alarmHandler: AlarmHandler

    @Inject
    lateinit var addMultipleTasks: AddMultipleTasks

    @Inject
    lateinit var removeRemoteTasks: RemoveRemoteTasks

    private lateinit var onSuccessListener: () -> Unit
    private lateinit var onFailureListener: () -> Unit

    suspend fun startSyncOperation() {
        getRemoteTasks.invoke(isOnline.hasNetworkConnection())
            .collect {
                when (it) {
                    is DataState.Success -> syncWithLocalDb(it.value)
                    is DataState.LocalError -> callFailureListener()
                    is DataState.NetworkError -> callFailureListener()
                    is DataState.NoInternet -> callFailureListener()
                    else -> {}
                }
            }
    }

    private suspend fun syncWithLocalDb(newRemoteTasks: List<Task>) {

        // get all local tasks
        getLocalTasks.invoke().collect { localTasks ->
            if (localTasks is DataState.Success) {
                // remove all remote tasks
                removeRemoteTasks.invoke()
                    .catch { callFailureListener() }
                    .collect { removeResult ->
                        if (removeResult is DataState.Success) {

                            // disable alarm for removed remote tasks
                            localTasks.value.filter { it.taskId != null }.forEach { task ->
                                alarmHandler.cancelTaskAlarm(task)
                            }

                            // add new remote tasks
                            addMultipleTasks.invoke(newRemoteTasks)
                                .catch { callFailureListener() }
                                .collect { addMultipleResult ->
                                    if (addMultipleResult is DataState.Success) {
                                        // reset alarms for remote tasks
                                        resetRemoteTaskAlarms()
                                    } else {
                                        callFailureListener()
                                    }
                                }
                        } else {
                            callFailureListener()
                        }
                    }

            } else {
                callFailureListener()
            }
        }
    }

    private suspend fun resetRemoteTaskAlarms() {
        getLocalTasks.invoke()
            .collect { tasks ->
                if (tasks is DataState.Success) {
                    tasks.value.filter { it.taskId != null }.forEach { task ->
                        alarmHandler.setAlarmForTask(task)
                    }
                    callSuccessListener()
                } else {
                    callFailureListener()
                }
            }
    }

    private fun callFailureListener() {
        if (::onFailureListener.isInitialized)
            onFailureListener.invoke()
    }

    private fun callSuccessListener() {
        if (::onSuccessListener.isInitialized)
            onSuccessListener.invoke()
    }

    fun setOnSuccessListener(onSuccessListener: () -> Unit) {
        this.onSuccessListener = onSuccessListener
    }

    fun setOnFailureListener(onFailureListener: () -> Unit) {
        this.onFailureListener = onFailureListener
    }

}