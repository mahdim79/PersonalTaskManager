package com.task.taskmanager.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.taskmanager.framework.local.entities.TaskEntity

@Database(entities = [ TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDataBase:RoomDatabase() {
    abstract fun getTaskDao():TaskDao
}