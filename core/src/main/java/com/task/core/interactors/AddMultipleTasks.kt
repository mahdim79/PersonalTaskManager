package com.task.core.interactors

import com.task.core.data.repositories.TaskRepository
import com.task.core.domain.task.Task

class AddMultipleTasks(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(tasks: List<Task>) = taskRepository.addMultipleTasks(tasks)
}