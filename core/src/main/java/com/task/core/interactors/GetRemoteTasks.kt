package com.task.core.interactors

import com.task.core.data.repositories.TaskRepository

class GetRemoteTasks(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(isOnline: Boolean) = taskRepository.fetchAllTasksFromApi(isOnline)
}