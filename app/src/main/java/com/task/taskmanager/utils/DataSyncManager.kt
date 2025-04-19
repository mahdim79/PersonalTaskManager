package com.task.taskmanager.utils

import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.AddMultipleTasks
import com.task.core.interactors.GetLocalTasks
import com.task.core.interactors.GetRemoteTasks
import javax.inject.Inject

class DataSyncManager @Inject constructor(){

    @Inject
    lateinit var getRemoteTasks: GetRemoteTasks

    @Inject
    lateinit var getLocalTasks: GetLocalTasks

    @Inject
    lateinit var isOnline:IsOnline

    @Inject
    lateinit var alarmHandler: AlarmHandler

    @Inject
    lateinit var addMultipleTasks: AddMultipleTasks

    suspend fun startSyncOperation() {
        getRemoteTasks.invoke(isOnline.hasNetworkConnection())
            .collect {
                if (it is DataState.Success) {
                    syncWithLocalDb(it.value)
                }
            }
    }

    private suspend fun syncWithLocalDb(remoteTasks: List<Task>){
        getLocalTasks.invoke().collect {
            if (it is DataState.Success) {
                val newTasks = calculateNotSyncedTasks(it.value, remoteTasks)
                if (newTasks.isNotEmpty()) {
                    addNewRemoteTasksToLocalDb(newTasks)
                }
            }
        }
    }

    private suspend fun resetAllAlarms() {
        getLocalTasks.invoke()
            .collect {
                if (it is DataState.Success) {
                    it.value.forEach { task ->
                        alarmHandler.setAlarmForTask(task)
                    }
                }
            }
    }

    private suspend fun addNewRemoteTasksToLocalDb(newTasks: List<Task>) {
        addMultipleTasks.invoke(newTasks)
            .collect {
                if (it is DataState.Success) {
                    if (it.value.isNotEmpty())
                        resetAllAlarms()
                }
            }
    }

    private fun calculateNotSyncedTasks(
        localTasks: List<Task>,
        remoteTasks: List<Task>
    ): List<Task> {
        val existingTaskIds = localTasks.mapNotNull { it.taskId }
        return remoteTasks.filter { !existingTaskIds.contains(it.taskId) }
    }
}