package com.davidbragadeveloper.todoapp.ui.taskdetail


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer

import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.base.BaseFragment
import com.davidbragadeveloper.todoapp.ui.edittask.EditTaskFragment
import com.davidbragadeveloper.todoapp.util.DateHelper
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.itemSelections
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_task_details.*
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.concurrent.TimeUnit


class TaskDetailsFragment : BaseFragment() {

    companion object {
        const val PARAM_ID = "id"
        fun newInstance(id: Long) = TaskDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(PARAM_ID, id)
            }
        }
    }

    var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments!!.getLong(PARAM_ID, -1L)

        if (id == -1L) {
            activity!!.onBackPressed()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(taskViewModel) {
            taskByIdEvent
                .observe(this@TaskDetailsFragment, Observer {
                    setUp(it)
                    setActions(it)
                })
        }

        taskViewModel.getTaskById(id)

    }

    private fun setActions(task: Task?) {
        isDoneCheckBox
            .clicks()
            .throttleFirst(600,TimeUnit.MILLISECONDS)
            .subscribe{
                task?.let {
                    val isChecked = !it.isDone
                    val updatedTask = it.copy(isDone = isChecked)
                    taskViewModel.updateTask(updatedTask)
                }
            }
            .addTo(compositeDisposable)
        taskCardView
            .clicks()
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe {
                showBottomSheetMenu(task!!){
                    activity?.onBackPressed()
                }
            }
            .addTo(compositeDisposable)
    }

    private fun setUp(task: Task) {
        tituloTextView.text = task.content
        dateTextView.text = DateHelper.calculateTimaAgo(task.createdAt)
        if (task.isHighPriority) {
            priorityChip.chipBackgroundColor = ColorStateList.valueOf(Color.RED)
            priorityChip.setText("High Priority")
        } else {
            priorityChip.chipBackgroundColor = ColorStateList.valueOf(Color.BLUE)
            priorityChip.setText("Low Priority")
        }

        if (task.isDone) {
            isDoneCheckBox.setTextColor(Color.RED)
            isDoneCheckBox.isChecked = true
        } else {
            isDoneCheckBox.setTextColor(Color.WHITE)
            isDoneCheckBox.isChecked = false
        }
    }


}
