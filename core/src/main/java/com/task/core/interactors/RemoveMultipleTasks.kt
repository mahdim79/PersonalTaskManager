package com.task.core.interactors

import com.task.core.data.repositories.TaskRepository
import com.task.core.domain.task.Task

class RemoveMultipleTasks(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(tasks:List<Task>) = taskRepository.removeMultipleTasks(tasks)
}