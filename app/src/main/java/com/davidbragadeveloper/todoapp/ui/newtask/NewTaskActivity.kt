package com.davidbragadeveloper.todoapp.ui.newtask

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.ui.base.BaseActivity
import com.davidbragadeveloper.todoapp.ui.TaskViewModel
import com.davidbragadeveloper.todoapp.util.Navigation
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_new_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class NewTaskActivity : BaseActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val taskViewModel : TaskViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        setUpToolbar(true)
        setTitle(R.string.new_task_title)

        setHint()
        bindObserver()
        bindActions()
    }

    private fun setHint() {
        textInputLayout.hint = getString(R.string.new_task_input_hint)
    }

    private fun bindObserver() {
        with(taskViewModel){
            addedEvent.observe(this@NewTaskActivity, Observer {
                if(!it.hasBeenHandled) {
                    it.getContentIfNotHandled()
                    setResult(Activity.RESULT_OK)
                    Navigation.navigateToTasksFragment(context = applicationContext)
                }

            })
        }
    }

    private fun bindActions() {
        newTaskButton
            .clicks()
            .throttleFirst(600, TimeUnit.MILLISECONDS)
            .subscribe {
                taskViewModel.addNew(inputTaskContent.text.toString(), priorityChip.getHighPriority())
            }
            .addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
