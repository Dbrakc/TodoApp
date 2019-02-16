package com.davidbragadeveloper.todoapp.ui.base

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.ui.SubtaskViewModel
import com.davidbragadeveloper.todoapp.ui.TaskViewModel
import com.davidbragadeveloper.todoapp.util.Navigation
import com.davidbragadeveloper.todoapp.util.botomsheet.BottomMenuItem
import com.davidbragadeveloper.todoapp.util.botomsheet.BottomSheetMenu
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseSubtasksFragment: Fragment() {

    val viewModel: SubtaskViewModel by viewModel()

    val compositeDisposable = CompositeDisposable()


    fun showBottomSheetMenu(it: Subtask, onDelete: (() -> Unit)? = null) {
        val items = arrayListOf(
            BottomMenuItem(R.drawable.ic_edit, getString(R.string.edit)) {
                Navigation.navigateToEditSubtaskFragment(it, childFragmentManager)
            },
            BottomMenuItem(R.drawable.ic_delete_black_24dp, getString(R.string.delete)) {
                showConfirmDeleteTaskDialog(it, onDelete)
            }
        )
        BottomSheetMenu(context!!, items).show()

    }

    private fun showConfirmDeleteTaskDialog(subtask: Subtask, onDelete: (() -> Unit)? = null) {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.task_delete_title)
            .setMessage(R.string.delete_task_message)
            .setPositiveButton(R.string.yes) { _: DialogInterface, i: Int ->
                this.viewModel.delete(subtask)
                onDelete?.invoke()
            }
            .setNegativeButton(R.string.no,null)
            .create()
            .show()
    }
}