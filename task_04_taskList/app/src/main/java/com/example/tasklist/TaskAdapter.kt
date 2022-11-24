package com.example.tasklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val context: Context,
    private val names: ArrayList<String>,
    private val descriptions: ArrayList<String>,
    private val datas: ArrayList<String>,
    private val statuses: ArrayList<String>,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    public class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameId: TextView = itemView.findViewById(R.id.name)
        var descriptionId: TextView = itemView.findViewById(R.id.description)
        var dataId: TextView = itemView.findViewById(R.id.data)
        var statusId: TextView = itemView.findViewById(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.nameId.text = this.names[position]
        holder.descriptionId.text = descriptions[position]
        holder.dataId.text = datas[position]
        holder.statusId.text = statuses[position]
    }

    override fun getItemCount(): Int {
        return names.size
    }
}