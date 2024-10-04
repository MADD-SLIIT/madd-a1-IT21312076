
package com.example.myagri

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class Task : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = mutableListOf<TaskModel>() // List to hold tasks
    private var selectedTaskPosition: Int? = null // Track selected task for updating
    private val db = FirebaseFirestore.getInstance() // Initialize Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        // Handle edge-to-edge view adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize RecyclerView and TaskAdapter
        recyclerView = findViewById(R.id.taskRecyclerView)
        taskAdapter = TaskAdapter(taskList, ::onTaskSelected)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        // Load existing tasks from Firestore
        loadTasks()

        setupTaskManagement()
    }

    // Load tasks from Firestore
    private fun loadTasks() {
        db.collection("tasks")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val task = document.toObject(TaskModel::class.java)
                    taskList.add(task)
                }
                taskAdapter.notifyDataSetChanged() // Notify adapter after loading tasks
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error loading tasks: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    // Setup add, update, delete logic for tasks
    private fun setupTaskManagement() {
        val addTaskButton: Button = findViewById(R.id.addTaskButton)
        val updateTaskButton: Button = findViewById(R.id.updateTaskButton)
        val deleteTaskButton: Button = findViewById(R.id.deleteTaskButton)
        val taskEditText: EditText = findViewById(R.id.taskEditText)

        // Add Task
        addTaskButton.setOnClickListener {
            val taskTitle = taskEditText.text.toString()
            if (taskTitle.isNotBlank()) {
                val newTask = TaskModel(taskList.size + 1, taskTitle)
                taskList.add(newTask) // Add a new task
                taskAdapter.notifyDataSetChanged() // Notify the adapter to refresh the RecyclerView
                taskEditText.text.clear() // Clear the input field

                // Save to Firestore
                db.collection("tasks").add(newTask)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Task added with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error adding task: $e", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        // Update Task
        updateTaskButton.setOnClickListener {
            selectedTaskPosition?.let { position ->
                val updatedTitle = taskEditText.text.toString()
                if (updatedTitle.isNotBlank()) {
                    val updatedTask = taskList[position]
                    updatedTask.title = updatedTitle // Update the task's title
                    taskAdapter.notifyItemChanged(position) // Notify the adapter to refresh the specific item
                    taskEditText.text.clear()
                    selectedTaskPosition = null // Clear selection after update

                    // Update in Firestore
                    db.collection("tasks").document(updatedTask.id.toString())
                        .set(updatedTask)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error updating task: $e", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "Select a task to update", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete Task
        deleteTaskButton.setOnClickListener {
            selectedTaskPosition?.let { position ->
                val taskToDelete = taskList[position]
                taskList.removeAt(position) // Remove the task from the list
                taskAdapter.notifyItemRemoved(position) // Notify the adapter to remove the item
                taskEditText.text.clear()
                selectedTaskPosition = null // Clear selection after delete

                // Delete from Firestore
                db.collection("tasks").document(taskToDelete.id.toString())
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error deleting task: $e", Toast.LENGTH_SHORT).show()
                    }
            } ?: run {
                Toast.makeText(this, "Select a task to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Callback for when a task is selected from the list
    private fun onTaskSelected(task: TaskModel) {
        selectedTaskPosition = taskList.indexOf(task) // Get the position of the selected task
        val taskEditText: EditText = findViewById(R.id.taskEditText)
        taskEditText.setText(task.title) // Set the selected task's title in the EditText
    }
}