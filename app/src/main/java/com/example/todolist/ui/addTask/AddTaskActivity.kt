package com.example.todolist.ui.addTask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.TaskApplication
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    private val addTaskViewModel: AddTaskViewModel by viewModels {
        AddTaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputDate.editText?.setOnClickListener { insertDate() }
        binding.inputHour.editText?.setOnClickListener { insertTime() }
        binding.btnCancel.setOnClickListener { finish() }

        if (intent.hasExtra("task")) {

            intent.getParcelableExtra<Task>("task")?.let {

                val copyTask: Task = it
                binding.inputTitle.text = it.title
                binding.inputDate.text = it.date
                binding.inputHour.text = it.hour

                binding.btnCreateTask.text = "Atualizar"
                binding.btnCreateTask.setOnClickListener {
                    updateTask(
                        Task(
                            id = copyTask.id,
                            title = binding.inputTitle.text,
                            hour = binding.inputDate.text,
                            date = binding.inputHour.text
                        )
                    )
                }
            }
        } else {
            binding.btnCreateTask.setOnClickListener { saveTask() }
        }
    }

    private fun updateTask(task: Task) {
        addTaskViewModel.update(task)
        finish()
    }

    private fun saveTask() {
        if (binding.inputTitle.text.trim() == "" ||
            binding.inputDate.text.trim() == "" ||
            binding.inputHour.text.trim() == ""
        ) {
            Toast.makeText(this, "Preencha os campos vazios", Toast.LENGTH_SHORT).show()
        } else {

            val task = Task(
                id = null,
                title = binding.inputTitle.text,
                date = binding.inputDate.text,
                hour = binding.inputHour.text
            )
            addTaskViewModel.save(task)
            finish()
        }
    }

    private fun insertDate() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()

        datePicker.addOnPositiveButtonClickListener {
            val timeZone = TimeZone.getDefault()
            val offset = timeZone.getOffset(Date().time) * -1
            binding.inputDate.text = Date(it + offset).format()
        }
        datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
    }

    private fun insertTime() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val minute =
                if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
            val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

            binding.inputHour.text = "$hour:$minute"
        }

        timePicker.show(supportFragmentManager, null)
    }

}