package com.task.core.data.datasources.remote

import com.task.core.domain.task.Task

interface TaskRemoteDataSource {
    suspend fun getAllTasks():List<Task>
}