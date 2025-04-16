package com.task.core.interactors

import com.task.core.data.repositories.TaskRepository

class GetTaskById(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(id:Int) = taskRepository.getTaskById(id)
}