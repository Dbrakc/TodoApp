package com.davidbragadeveloper.todoapp.ui.edittask


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.TaskViewModel
import com.davidbragadeveloper.todoapp.util.BottomSheetDialog
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_edit_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 *
 */
class EditTaskFragment : BottomSheetDialog() {

    companion object {
        const val PARAM_TASK = "subtask"
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
        setHint()
        fillData()
        bindEvents()
        bindActions()
    }

    fun bindActions() {
        editTaskButton
            .clicks()
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe() {
                val newTask = task!!.copy(
                    content = inputTaskContent.text.toString(),
                    highPriority = priorityChip.getHighPriority()
                )
                taskViewModel.update(newTask)
            }
            .addTo(compositeDisposable)
    }

    fun bindEvents() {
        with(taskViewModel) {
            updateEvent.observe(this@EditTaskFragment, Observer {
                dismiss()
            })
        }
    }

    private fun setHint() {
        textInputLayout.hint = getString(R.string.edit_task_input_hint)
    }

    fun fillData() {
        requireNotNull(task) {
            "Task is null dailog should be closed"
        }



        task?.let {
            inputTaskContent.setText(it.content)
            priorityChip.setColorForHighPriority(it.highPriority)
        }
    }

}
