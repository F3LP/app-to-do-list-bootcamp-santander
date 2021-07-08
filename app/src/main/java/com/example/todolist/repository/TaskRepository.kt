package com.example.todolist.repository

import com.example.todolist.data.TaskDao
import com.example.todolist.model.Task
import kotlinx.coroutines.flow.Flow


class TaskRepository (private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAll()

    suspend fun save(task: Task) = taskDao.save(task)

    suspend fun delete(task: Task) = taskDao.delete(task)

    suspend fun update(task: Task) = taskDao.update(task)
}