package com.davidbragadeveloper.todoapp.ui.newsubtask

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.SubtaskViewModel
import com.davidbragadeveloper.todoapp.ui.base.BaseActivity
import com.davidbragadeveloper.todoapp.ui.edittask.EditTaskFragment.Companion.PARAM_TASK
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_new_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class NewSubtaskActivity () : BaseActivity() {
    lateinit var task: Task
    private val subtaskViewModel : SubtaskViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        setUpToolbar(true)
        setTitle(R.string.new_subtask_title)
        val extras = intent.extras
        task = extras?.getParcelable(PARAM_TASK)!!

        setHint()
        bindObserver()
        bindActions()

    }

    private fun setHint() {
        textInputLayout.hint = getString(R.string.new_subtask_input_hint)
    }


    private fun bindObserver() {
        with(subtaskViewModel){
            subtaskAddedEvent.observe(this@NewSubtaskActivity, Observer {
                if(!it.hasBeenHandled) {
                    it.getContentIfNotHandled()
                    setResult(Activity.RESULT_OK)
                    this@NewSubtaskActivity.onBackPressed()
                }

            })
        }
    }

    private fun bindActions() {
        newTaskButton
            .clicks()
            .throttleFirst(600, TimeUnit.MILLISECONDS)
            .subscribe {
                subtaskViewModel.addNew(inputTaskContent.text.toString(), priorityChip.getHighPriority(),task)
            }
            .addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }




}
