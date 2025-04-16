package com.task.taskmanager.framework.local.datasources

import com.task.core.data.datasources.local.TaskLocalDataSource
import com.task.core.domain.task.Task
import com.task.taskmanager.framework.local.TaskDao
import com.task.taskmanager.framework.local.mapper.TaskLocalMapper

class TaskLocalDataSourceImpl(private val taskDao: TaskDao,private val taskLocalMapper: TaskLocalMapper): TaskLocalDataSource {
    override suspend fun getAllTasks(): List<Task> {
        return taskLocalMapper.mapToDomainList(taskDao.fetchAllTasks())
    }

    override suspend fun addNewTask(task: Task) {
        taskDao.addNewTask(taskLocalMapper.mapToEntity(task))
    }

    override suspend fun removeTask(id: Int) {
        taskDao.deleteTask(id)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(taskLocalMapper.mapToEntity(task))
    }
}