package com.task.core.interactors

import com.task.core.data.repositories.TaskRepository

class RemoveTask(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(id:Int) = taskRepository.deleteTask(id)
}