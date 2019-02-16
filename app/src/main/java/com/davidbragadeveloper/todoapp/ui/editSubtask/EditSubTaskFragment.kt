package com.davidbragadeveloper.todoapp.ui.editSubtask


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.SubtaskViewModel
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
class EditSubTaskFragment : BottomSheetDialog() {

    companion object {
        const val PARAM_SUBTASK = "subtask"
        fun newInstance(subtask: Subtask): EditSubTaskFragment = EditSubTaskFragment().apply {
            arguments= Bundle().apply {
                putParcelable(PARAM_SUBTASK, subtask)
            }
        }
    }


    val subTaskViewModel: SubtaskViewModel by viewModel()

    var subtask: Subtask? = null

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subtask = arguments?.getParcelable(PARAM_SUBTASK)


        if (subtask == null) {
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
        editTaskButton
            .clicks()
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe() {
                val newSubtask = subtask!!.copy(
                    content = inputTaskContent.text.toString(),
                    isHighPriority = highPriorityCheckBox.isChecked
                )
                subTaskViewModel.update(newSubtask)
            }
            .addTo(compositeDisposable)
    }

    fun bindEvents() {
        with(subTaskViewModel) {
            subtaskUpdateEvent.observe(this@EditSubTaskFragment, Observer {
                dismiss()
            })
        }
    }



    fun fillData() {
        requireNotNull(subtask) {
            "Task is null dailog should be closed"
        }

        subtask?.let {
            inputTaskContent.setText(it.content)
            highPriorityCheckBox.isChecked = it.isHighPriority
        }
    }

}
