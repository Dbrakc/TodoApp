package com.davidbragadeveloper.todoapp.data.repository

import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.repository.local.LocalDataSource
import io.reactivex.Flowable

interface SubtaskRepository  {
    fun observeAllSubtasks(taskId: Long): Flowable<List<Subtask>>

    fun insertSubtask(subtaskEntity: Subtask)

    fun removeSubtask(subtaskEntity: Subtask)

    fun updateSubtask(subtaskEntity: Subtask)
}