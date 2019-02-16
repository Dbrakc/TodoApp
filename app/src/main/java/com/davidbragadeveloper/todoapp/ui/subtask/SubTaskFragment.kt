package com.davidbragadeveloper.todoapp.ui.subtask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.ui.base.BaseSubtasksFragment
import com.davidbragadeveloper.todoapp.util.Navigation
import kotlinx.android.synthetic.main.fragment_tasks.*



class SubTaskFragment  : BaseSubtasksFragment() {

    companion object {
        const val PARAM_ID = "param_id"
        fun newInstance(id: Long) = SubTaskFragment().apply{
            arguments = Bundle().apply {
                putLong(PARAM_ID, id)
            }

        }
    }


    var id : Long = -1L

    val adapter: SubtaskAdapter by lazy {
        SubtaskAdapter(
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
            onTaskHighPriorityMarked = { task, isHighPriority ->
                viewModel.markAsHighPriority(task, isHighPriority)

            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments!!.getLong(PARAM_ID, -1L)

        viewModel.load(id);

        if(id == -1L){
            activity!!.onBackPressed()
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tasks,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUp()
        with(viewModel) {
            subtaskLoadEvent.observe(this@SubTaskFragment, Observer {
                adapter.submitList(it.map {
                    it as Subtask
                })
            })
            subtaskUpdateEvent.observe(this@SubTaskFragment, Observer {

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
