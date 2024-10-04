package com.example.myagri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val taskList: List<TaskModel>, // List of tasks
    private val onItemClick: (TaskModel) -> Unit // Lambda function for item click
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder class to hold the item view
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)

        fun bind(task: TaskModel) {
            taskTitle.text = task.title // Set the title to the TextView
            itemView.setOnClickListener { onItemClick(task) } // Handle item click
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false) // Inflate item layout
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position] // Get the task at the given position
        holder.bind(task) // Bind the task to the holder
    }

    override fun getItemCount(): Int {
        return taskList.size // Return the size of the task list
    }
}
