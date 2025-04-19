package com.task.taskmanager.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.task.taskmanager.framework.local.entities.TaskEntity

@Dao
interface TaskDao {
    @Insert(entity = TaskEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewTask(task: TaskEntity):Long

    @Insert(entity = TaskEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMultipleTasks(tasks: List<TaskEntity>):List<Long>

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Int)

    @Query("DELETE FROM tasks WHERE taskId IS NOT NULL")
    suspend fun deleteRemoteTasks():Int

    @Update(entity = TaskEntity::class)
    suspend fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    suspend fun fetchAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun fetchTaskById(id:Int): List<TaskEntity>
}