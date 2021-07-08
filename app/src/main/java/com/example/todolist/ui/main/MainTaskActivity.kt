package com.example.todolist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.TaskApplication
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.ui.addTask.AddTaskActivity


class MainTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy { TaskAdapter() }

    private val taskViewModel: MainTaskViewModel by viewModels {
        MainTaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_tasks)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        insertListeners()

        taskViewModel.allTasks.observe(this, { tasks ->
            if (tasks.isEmpty()) {
                binding.includeEmptyState.emptyState.visibility = View.VISIBLE
            } else {
                binding.includeEmptyState.emptyState.visibility = View.GONE
            }
            tasks?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun insertListeners() {

        binding.fab.setOnClickListener {
            goToPageNewTask()
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("task", it)
            startActivity(intent)
        }

        adapter.listenerDelete = {
            taskViewModel.delete(it)
        }
    }

    private fun goToPageNewTask() {
        val intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)
    }
}

