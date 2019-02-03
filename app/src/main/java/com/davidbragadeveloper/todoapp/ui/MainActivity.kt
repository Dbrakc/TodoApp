package com.davidbragadeveloper.todoapp.ui

import android.os.Bundle
import android.app.Activity
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.ui.base.BaseActivity
import com.davidbragadeveloper.todoapp.ui.taskdetail.TaskDetailsFragment
import com.davidbragadeveloper.todoapp.ui.tasks.TaskViewModel
import com.davidbragadeveloper.todoapp.ui.tasks.TasksFragment
import com.davidbragadeveloper.todoapp.util.Const
import com.davidbragadeveloper.todoapp.util.Navigation
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_bottom_sheet_menu.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {


    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar(false)
        setTitle(R.string.app_name)
        setUp()

    }

    private fun setUp() {
        bindActions()
        Navigation.navigateToTasksFragment(supportFragmentManager)
    }

    private fun bindActions(){

        fab
            .clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe{
                Navigation.navigationToNewTaskActivity(this)
            }
            .addTo(compositeDisposable)

    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onBackPressed() {
        val fragmentDetails = supportFragmentManager.findFragmentByTag(Const.FRAGMENT_DETAILS)

        fragmentDetails?.let {
            if(it.isVisible) {
                Navigation.navigateToTasksFragment(supportFragmentManager)
                return
            }
        }
        super.onBackPressed()
    }



}
