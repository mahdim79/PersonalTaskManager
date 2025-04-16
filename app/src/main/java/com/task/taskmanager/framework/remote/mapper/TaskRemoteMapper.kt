package com.task.taskmanager.framework.remote.mapper

import com.task.core.domain.task.Task
import com.task.taskmanager.framework.remote.dto.TaskDto
import com.task.taskmanager.framework.utils.EntityMapper
import javax.inject.Inject

class TaskRemoteMapper @Inject constructor() : EntityMapper<Task, TaskDto> {
    override fun mapToEntity(domain: Task): TaskDto {
        return TaskDto(domain.title, domain.description, domain.time)
    }

    override fun mapToDomain(entity: TaskDto): Task {
        return Task(
            id = null,
            title = entity.title,
            description = entity.description,
            time = entity.time
        )
    }
}