package com.task.taskmanager.framework.remote

import com.task.taskmanager.framework.remote.dto.TaskDto
import com.task.taskmanager.utils.Constants
import retrofit2.http.GET

interface TaskApiService {
    @GET(Constants.ENDPOINT_GET_ALL_TASKS)
    suspend fun fetchAllTasks():List<TaskDto>
}