package com.task.core.data.repositories

import com.task.core.data.datasources.local.TaskLocalDataSource
import com.task.core.data.datasources.remote.TaskRemoteDataSource
import com.task.core.domain.task.Task
import com.task.core.helper.ApiCallHelper
import com.task.core.helper.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TaskRepository(
    private val localDataSource: TaskLocalDataSource,
    private val remoteDataSource: TaskRemoteDataSource
) {
    suspend fun addNewTask(task: Task) = flow {
        try {
            emit(DataState.Success(localDataSource.addNewTask(task)))
        } catch (e: Exception) {
            emit(DataState.LocalError(e.message))
        }
    }

    suspend fun deleteTask(id: Int) = flow {
        try {
            emit(DataState.Success(localDataSource.removeTask(id)))
        } catch (e: Exception) {
            emit(DataState.LocalError(e.message))
        }
    }

    suspend fun updateTask(task: Task) = flow {
        try {
            emit(DataState.Success(localDataSource.updateTask(task)))
        } catch (e: Exception) {
            emit(DataState.LocalError(e.message))
        }
    }

    suspend fun getAllTasks() = flow {
        try {
            emit(DataState.Success(localDataSource.getAllTasks()))
        } catch (e: Exception) {
            emit(DataState.LocalError(e.message))
        }
    }

    suspend fun getTaskById(id:Int) = flow {
        try {
            emit(DataState.Success(localDataSource.getTaskById(id)))
        } catch (e: Exception) {
            emit(DataState.LocalError(e.message))
        }
    }

    suspend fun fetchAllTasksFromApi(isOnline: Boolean) = flow {
        if (isOnline) {
            emit(DataState.Loading)
            ApiCallHelper.safeApiCall {
                remoteDataSource.getAllTasks()
            }.catch {
                emit(DataState.LocalError(it.message))
            }.collect {
                emit(it)
            }
        } else {
            emit(DataState.NoInternet)
        }
    }

}