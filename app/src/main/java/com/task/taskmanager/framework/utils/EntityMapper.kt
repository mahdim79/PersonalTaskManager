package com.task.taskmanager.framework.utils

interface EntityMapper<Domain,Entity> {
    fun mapToEntity(domain: Domain):Entity
    fun mapToDomain(entity: Entity):Domain
    fun mapToEntityList(domains: List<Domain>):List<Entity> = domains.map { mapToEntity(it) }
    fun mapToDomainList(entities: List<Entity>):List<Domain> = entities.map { mapToDomain(it) }
}