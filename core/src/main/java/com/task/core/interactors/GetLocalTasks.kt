package com.task.core.interactors

import com.task.core.data.repositories.TaskRepository

class GetLocalTasks(private val taskRepository: TaskRepository) {
    suspend operator fun invoke() = taskRepository.getAllTasks()
}