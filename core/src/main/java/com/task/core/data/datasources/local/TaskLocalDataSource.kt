package com.task.core.data.datasources.local

import com.task.core.domain.task.Task

interface TaskLocalDataSource {
    suspend fun getAllTasks():List<Task>
    suspend fun getTaskById(id:Int):Task
    suspend fun addNewTask(task: Task):Long
    suspend fun addMultipleTasks(tasks: List<Task>):List<Long>
    suspend fun removeMultipleTasks(tasks:List<Task>)
    suspend fun removeTask(id:Int)
    suspend fun updateTask(task: Task)
}