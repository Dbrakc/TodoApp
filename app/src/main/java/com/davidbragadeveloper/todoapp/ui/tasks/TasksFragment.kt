package com.davidbragadeveloper.todoapp.ui.tasks

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.base.BaseFragment
import com.davidbragadeveloper.todoapp.util.Navigation
import com.davidbragadeveloper.todoapp.util.botomsheet.BottomMenuItem
import com.davidbragadeveloper.todoapp.util.botomsheet.BottomSheetMenu
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : BaseFragment() {

    val adapter: TaskAdapter by lazy {
        TaskAdapter(
            onTaskClicked = {
                Navigation.navigateToDetail(it, fragmentManager!!)
            },
            onTaskLongClicked = {
                showBottomSheetMenu(it)
            },
            onTaskMarked = { task, isDone ->
                if(isDone){
                    taskViewModel.markAsDone(task)
                }else{
                    taskViewModel.markAsNotDone(task)
                }
            },
            onTaskHighPriorityMarked = { task, isHighPriority ->
                taskViewModel.markAsHighPriority(task, isHighPriority)

            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
        with (taskViewModel) {
            tasksEvent.observe(this@TasksFragment, Observer {
                adapter.submitList(it)
            })
            updateTaskEvent.observe(this@TasksFragment, Observer {

            })
        }
    }

    private fun setUp() {
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        taskRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        taskRecycler.itemAnimator = DefaultItemAnimator()
        taskRecycler.adapter = adapter
    }


}
