package com.davidbragadeveloper.todoapp.ui.edittask


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.tasks.TaskViewModel
import com.davidbragadeveloper.todoapp.util.BottomSheetDialog
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_edit_task.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 *
 */
class EditTaskFragment : BottomSheetDialog() {

    companion object {
        const val PARAM_TASK = "task"
        fun newInstance(task: Task): EditTaskFragment = EditTaskFragment().apply {
            arguments= Bundle().apply {
                putParcelable(PARAM_TASK, task)
            }
        }
    }


    val taskViewModel: TaskViewModel by viewModel()

    var task: Task? = null

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        task = arguments?.getParcelable(PARAM_TASK)


        if (task == null) {
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUp()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setUp() {
        fillData()
        bindEvents()
        bindActions()
    }

    fun bindActions() {
        requireNotNull(task) {
            "Task is null dailog should be closed"
        }

        inputTaskContent.setText(task!!.content)

    }

    fun bindEvents() {
        with(taskViewModel) {
            updateTaskEvent.observe(this@EditTaskFragment, Observer {
                dismiss()
            })
        }
    }



    fun fillData() {
        editTaskButton
            .clicks()
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe() {
                taskViewModel.updateTaskContent(task!!, inputTaskContent.text.toString())
            }
            .addTo(compositeDisposable)
    }

}
