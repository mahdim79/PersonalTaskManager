package com.task.core.data.datasources.local

import com.task.core.domain.task.Task

interface TaskLocalDataSource {
    suspend fun getAllTasks():List<Task>
    suspend fun addNewTask(task: Task)
    suspend fun removeTask(id:Int)
    suspend fun updateTask(task: Task)
}