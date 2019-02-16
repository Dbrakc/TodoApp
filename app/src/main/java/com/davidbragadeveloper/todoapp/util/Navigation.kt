package com.davidbragadeveloper.todoapp.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.MainActivity
import com.davidbragadeveloper.todoapp.ui.editSubtask.EditSubTaskFragment
import com.davidbragadeveloper.todoapp.ui.edittask.EditTaskFragment
import com.davidbragadeveloper.todoapp.ui.newsubtask.NewSubtaskActivity
import com.davidbragadeveloper.todoapp.ui.newtask.NewTaskActivity
import com.davidbragadeveloper.todoapp.ui.edittask.EditTaskFragment.Companion.PARAM_TASK
import com.davidbragadeveloper.todoapp.ui.taskdetail.TaskDetailsFragment
import com.davidbragadeveloper.todoapp.ui.tasks.TasksFragment

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

    fun navigateToDetail(id: Long, fragmentManager: FragmentManager) {
        val fragment = TaskDetailsFragment.newInstance(id)
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, Const.FRAGMENT_DETAILS)
            .commit()
    }

    fun navigateToTasksFragment(fragmentManager: FragmentManager? = null, context: Context? = null) {
        fragmentManager?.let{
            it
            .beginTransaction()
            .replace(R.id.fragmentContainer,TasksFragment.newInstance())
            .commit()
            return
        }

        context?.let {
            val intent = Intent(it, MainActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK
            it.startActivity(intent)
        }


    }

    fun navigateToNewSubtaskActivity(task: Task, context: Context) {
        with(context) {
            val intent = Intent(this, NewSubtaskActivity::class.java)
            val extras = Bundle()
            Log.d("navigation", task.id.toString())
            extras.putParcelable(PARAM_TASK,task)
            intent.putExtras(extras)
            startActivity(intent)
        }

    }

    fun navigateToEditSubtaskFragment(subtask: Subtask, childFragmentManager: FragmentManager) {
        val fragment = EditSubTaskFragment.newInstance(subtask)
        fragment.show(childFragmentManager,null)
    }

}