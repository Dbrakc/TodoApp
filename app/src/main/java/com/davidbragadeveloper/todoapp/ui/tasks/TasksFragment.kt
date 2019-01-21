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
import com.davidbragadeveloper.todoapp.util.Navigation
import com.davidbragadeveloper.todoapp.util.botomsheet.BottomMenuItem
import com.davidbragadeveloper.todoapp.util.botomsheet.BottomSheetMenu
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : Fragment() {

    val taskViewModel: TaskViewModel by viewModel()
    val adapter: TaskAdapter by lazy {
        TaskAdapter(
            onTaskClicked = {
                //TODO navigate to detail
            },
            onTaskLongClicked = {
                val items = arrayListOf(
                    BottomMenuItem(R.drawable.ic_delete_black_24dp, getString(R.string.delete)){
                        showConfirmDeleteTaskDialog(it)
                    },
                    BottomMenuItem(R.drawable.ic_edit,getString(R.string.edit)){
                        Navigation.navigateToEditFragment(it, childFragmentManager)
                    }
                )
                BottomSheetMenu(context!! ,items).show()
            },
            onTaskMarked = { task, isDone ->
                if(isDone){
                    taskViewModel.markAsDone(task)
                }else{
                    taskViewModel.markAsNotDone(task)
                }
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

    private fun showConfirmDeleteTaskDialog(task: Task) {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.task_delete_title)
            .setMessage(R.string.delete_task_message)
            .setPositiveButton(R.string.yes) { _: DialogInterface, i: Int ->
                taskViewModel.deleteTask(task)
            }
            .setNegativeButton(R.string.no,null)
            .create()
            .show()
    }
}