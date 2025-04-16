package com.task.taskmanager.di.modules

import com.task.core.data.datasources.local.TaskLocalDataSource
import com.task.core.data.datasources.remote.TaskRemoteDataSource
import com.task.core.data.repositories.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepository(
        taskLocalDataSource: TaskLocalDataSource,
        taskRemoteDataSource: TaskRemoteDataSource
    ): TaskRepository = TaskRepository(taskLocalDataSource,taskRemoteDataSource)
}