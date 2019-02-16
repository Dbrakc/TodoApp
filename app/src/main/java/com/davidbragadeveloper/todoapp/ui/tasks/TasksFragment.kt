package com.davidbragadeveloper.todoapp.ui.tasks

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.base.BaseTasksFragment
import com.davidbragadeveloper.todoapp.util.ListPaddingDecoration
import com.davidbragadeveloper.todoapp.util.Navigation
import kotlinx.android.synthetic.main.fragment_tasks.*



class TasksFragment  : BaseTasksFragment() {

    companion object {
        fun newInstance() = TasksFragment()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

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
                    viewModel.markAsDone(task)
                }else{
                    viewModel.markAsNotDone(task)
                }
            },
            onTaskHighPriorityMarked = { task, highPriority ->
                viewModel.markAsHighPriority(task, highPriority)

            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
        with(viewModel) {
            loadEvent.observe(this@TasksFragment, Observer {
                adapter.submitList(it.map {
                    it as? Task
                })
            })
            updateEvent.observe(this@TasksFragment, Observer {

            })
        }
    }

    private fun setUp() {
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        taskRecycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        taskRecycler.itemAnimator = DefaultItemAnimator()
        taskRecycler.addItemDecoration(
            ListPaddingDecoration(
            activity as Activity,
             0,
            0
        ))
        taskRecycler.adapter = adapter
    }



}
