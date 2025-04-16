package com.task.taskmanager.framework.remote.datasources

import com.task.core.data.datasources.remote.TaskRemoteDataSource
import com.task.core.domain.task.Task
import com.task.taskmanager.framework.remote.TaskApiService
import com.task.taskmanager.framework.remote.mapper.TaskRemoteMapper

class TaskRemoteDataSourceImpl(private val apiService: TaskApiService,private val taskRemoteMapper: TaskRemoteMapper) :
    TaskRemoteDataSource {
    override suspend fun getAllTasks(): List<Task> {
        return taskRemoteMapper.mapToDomainList(apiService.fetchAllTasks())
    }
}