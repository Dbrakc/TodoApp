package com.davidbragadeveloper.todoapp.util

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.edittask.EditTaskFragment
import com.davidbragadeveloper.todoapp.ui.newtask.NewTaskActivity

object Navigation{

    fun navigationToNewTaskActivity(context: Context){
        with(context) {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
        }
    }

    fun navigateToEditFragment (task: Task, fragmentManager: FragmentManager){
        val fragment = EditTaskFragment.newInstance(task)
        fragment.show(fragmentManager,null)
    }
}