package com.example.todolist.ui.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.Task
import com.example.todolist.repository.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel (private val repository: TaskRepository) : ViewModel() {

    fun save(task: Task) = viewModelScope.launch { repository.save(task) }

    fun update(task: Task) = viewModelScope.launch { repository.update(task) }
}

class AddTaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}