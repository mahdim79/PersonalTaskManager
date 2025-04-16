package com.task.taskmanager.framework.remote.mapper

import com.task.core.domain.task.Task
import com.task.taskmanager.framework.remote.dto.TaskDto
import com.task.taskmanager.framework.utils.EntityMapper

class TaskRemoteMapper:EntityMapper<Task,TaskDto> {
    override fun mapToEntity(domain: Task): TaskDto {
        TODO("Not yet implemented")
    }

    override fun mapToDomain(entity: TaskDto): Task {
        TODO("Not yet implemented")
    }
}