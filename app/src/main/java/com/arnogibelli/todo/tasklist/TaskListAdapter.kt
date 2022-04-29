package com.arnogibelli.todo.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arnogibelli.todo.R

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var currentList: List<Task> = emptyList()
    var onClickDelete: (Task) -> Unit = {}
    var onClickEdit: (Task) -> Unit = {}

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            val textsArguments = itemView.findViewById<TextView>(R.id.task_title)
            textsArguments.text = task.title
            val textsArguments2 = itemView.findViewById<TextView>(R.id.task_description)
            textsArguments2.text = task.description
            val buttondeleted = itemView.findViewById<ImageButton>(R.id.image_Button)
            buttondeleted.setOnClickListener{
                onClickDelete(task)
            }
            val buttondedit = itemView.findViewById<ImageButton>(R.id.imageButton3)
                buttondedit.setOnClickListener{
                    onClickEdit(task)
                }

        }
    }

    override fun getItemCount(): Int {
        return currentList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}


