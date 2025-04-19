package com.task.taskmanager.di.modules

import com.task.core.data.repositories.TaskRepository
import com.task.core.interactors.AddMultipleTasks
import com.task.core.interactors.AddTask
import com.task.core.interactors.GetLocalTasks
import com.task.core.interactors.GetRemoteTasks
import com.task.core.interactors.GetTaskById
import com.task.core.interactors.RemoveRemoteTasks
import com.task.core.interactors.RemoveTask
import com.task.core.interactors.UpdateTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideAddTask(taskRepository: TaskRepository): AddTask = AddTask(taskRepository)

    @Provides
    @Singleton
    fun provideAddMultipleTask(taskRepository: TaskRepository): AddMultipleTasks = AddMultipleTasks(taskRepository)


    @Provides
    @Singleton
    fun provideRemoveTask(taskRepository: TaskRepository): RemoveTask = RemoveTask(taskRepository)

    @Provides
    @Singleton
    fun provideRemoveRemoteTasks(taskRepository: TaskRepository): RemoveRemoteTasks = RemoveRemoteTasks(taskRepository)


    @Provides
    @Singleton
    fun provideGetLocalTasks(taskRepository: TaskRepository): GetLocalTasks =
        GetLocalTasks(taskRepository)

    @Provides
    @Singleton
    fun provideGetRemoteTasks(taskRepository: TaskRepository): GetRemoteTasks =
        GetRemoteTasks(taskRepository)

    @Provides
    @Singleton
    fun provideUpdateTask(taskRepository: TaskRepository): UpdateTask = UpdateTask(taskRepository)

    @Provides
    @Singleton
    fun provideGetTaskById(taskRepository: TaskRepository): GetTaskById = GetTaskById(taskRepository)


}