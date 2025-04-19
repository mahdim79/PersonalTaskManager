package com.task.taskmanager.utils

import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.AddMultipleTasks
import com.task.core.interactors.GetLocalTasks
import com.task.core.interactors.GetRemoteTasks
import com.task.core.interactors.RemoveMultipleTasks
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
    lateinit var removeMultipleTasks: RemoveMultipleTasks

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
        getLocalTasks.invoke()
            .catch { callFailureListener() }
            .collect { localTasks ->
            if (localTasks is DataState.Success) {
                val removedTasks = calculateRemovedTasks(localTasks.value,newRemoteTasks)
                val newTasks = calculateNewTasks(localTasks.value,newRemoteTasks)

                // delete tasks that removed from api and exist in local db
                removeMultipleTasks.invoke(removedTasks)
                    .catch { callFailureListener() }
                    .collect { removeResult ->
                        if (removeResult is DataState.Success){

                            removedTasks.forEach {
                                alarmHandler.cancelTaskAlarm(it)
                            }

                            // add tasks that have been added to api and don't exist in local db
                            addMultipleTasks.invoke(newTasks)
                                .catch { callFailureListener() }
                                .collect{
                                    if (it is DataState.Success){
                                        reScheduleRemoteAlarms()
                                    }else{
                                        callFailureListener()
                                    }
                                }

                        }else{
                            callFailureListener()
                        }
                    }

            } else {
                callFailureListener()
            }
        }
    }

    private suspend fun reScheduleRemoteAlarms() {
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

    private fun calculateRemovedTasks(localTasks:List<Task>,remoteTasks:List<Task>):List<Task>{
        val remoteTaskIds = remoteTasks.mapNotNull { it.taskId }
        return localTasks.filter { !remoteTaskIds.contains(it.taskId) && it.taskId != null }
    }

    private fun calculateNewTasks(localTasks:List<Task>,remoteTasks:List<Task>):List<Task>{
        val localTaskIds = localTasks.mapNotNull { it.taskId }
        return remoteTasks.filter { !localTaskIds.contains(it.taskId) }
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