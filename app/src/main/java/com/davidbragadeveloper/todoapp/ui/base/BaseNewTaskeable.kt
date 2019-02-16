package com.davidbragadeveloper.todoapp.ui.base

import android.app.Activity
import android.os.Bundle
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Taskeable
import com.davidbragadeveloper.todoapp.ui.TaskViewModel
import com.davidbragadeveloper.todoapp.util.Navigation
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_new_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

abstract class BaseNewTaskeable <T : Taskeable> : BaseActivity(){

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        setUpToolbar(true)
        setTitle(R.string.new_task_title)

        bindObserver()
        bindActions()
    }

    abstract fun bindObserver()

    private fun bindActions() {
        newTaskButton
            .clicks()
            .throttleFirst(600, TimeUnit.MILLISECONDS)
            .subscribe {
                onAddNewButtonClick(inputTaskContent.text.toString(), highPriorityCheckBox.isChecked)
            }
            .addTo(compositeDisposable) }

    abstract fun onAddNewButtonClick(content: String, isHighPriority: Boolean)

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}

