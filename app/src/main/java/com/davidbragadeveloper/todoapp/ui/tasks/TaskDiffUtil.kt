package com.davidbragadeveloper.todoapp.ui.tasks

import androidx.recyclerview.widget.DiffUtil
import com.davidbragadeveloper.todoapp.data.model.Task

class TaskDiffUtil: DiffUtil.ItemCallback<Task> (){

    companion object {
        fun getInstance() = TaskDiffUtil()
    }

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem

}