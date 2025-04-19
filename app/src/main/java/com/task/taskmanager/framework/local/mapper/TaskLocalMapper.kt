package com.task.taskmanager.framework.local.mapper

import com.task.core.domain.task.Task
import com.task.taskmanager.framework.local.entities.TaskEntity
import com.task.taskmanager.framework.utils.EntityMapper
import javax.inject.Inject

class TaskLocalMapper @Inject constructor() : EntityMapper<Task, TaskEntity> {
    override fun mapToEntity(domain: Task): TaskEntity =
        TaskEntity(domain.id, domain.title, domain.description, domain.time,domain.taskId)

    override fun mapToDomain(entity: TaskEntity): Task =
        Task(entity.id, entity.title, entity.description, entity.time,entity.taskId)

}