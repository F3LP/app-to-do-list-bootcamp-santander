package com.example.todolist.ui.main

import androidx.lifecycle.*
import com.example.todolist.model.Task
import com.example.todolist.repository.TaskRepository
import kotlinx.coroutines.launch


class MainTaskViewModel (private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.getAllTasks().asLiveData()

    fun delete(task: Task) = viewModelScope.launch { repository.delete(task) }
}


class MainTaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainTaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainTaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}