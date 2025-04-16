package com.task.taskmanager.framework.utils

interface EntityMapper<Domain,Entity> {
    fun mapToEntity(domain: Domain):Entity
    fun mapToDomain(domain: Entity):Domain
}