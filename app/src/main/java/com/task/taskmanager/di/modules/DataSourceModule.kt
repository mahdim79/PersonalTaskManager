package com.task.taskmanager.di.modules

import com.task.core.data.datasources.local.TaskLocalDataSource
import com.task.core.data.datasources.remote.TaskRemoteDataSource
import com.task.taskmanager.framework.local.TaskDao
import com.task.taskmanager.framework.local.datasources.TaskLocalDataSourceImpl
import com.task.taskmanager.framework.local.mapper.TaskLocalMapper
import com.task.taskmanager.framework.remote.TaskApiService
import com.task.taskmanager.framework.remote.datasources.TaskRemoteDataSourceImpl
import com.task.taskmanager.framework.remote.mapper.TaskRemoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideTaskLocalDataSource(
        taskDao: TaskDao,
        taskLocalMapper: TaskLocalMapper
    ): TaskLocalDataSource = TaskLocalDataSourceImpl(taskDao,taskLocalMapper)

    @Provides
    @Singleton
    fun provideTaskRemoteDataSource(
        apiService: TaskApiService,
        taskRemoteMapper: TaskRemoteMapper
    ): TaskRemoteDataSource = TaskRemoteDataSourceImpl(apiService,taskRemoteMapper)

}