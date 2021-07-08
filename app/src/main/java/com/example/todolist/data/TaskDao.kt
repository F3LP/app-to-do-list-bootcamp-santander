package com.example.todolist.data

import androidx.room.*
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(task: Task)

    @Query("SELECT * FROM task")
    fun getAll(): Flow<List<Task>>

    @Query("DELETE FROM task")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)
}