package com.example.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.Task

class TaskAdapter(private val tasks: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameId: TextView = itemView.findViewById(R.id.name_id)
        var descriptionId: TextView = itemView.findViewById(R.id.description_id)
        var dataId: TextView = itemView.findViewById(R.id.data_id)
        var statusId: TextView = itemView.findViewById(R.id.status_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = tasks[position]
        holder.nameId.text=item.name
        holder.descriptionId.text=item.description
        holder.dataId.text=item.data
        holder.statusId.text=item.status

    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}