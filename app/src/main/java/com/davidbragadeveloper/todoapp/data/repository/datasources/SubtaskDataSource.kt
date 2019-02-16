package com.davidbragadeveloper.todoapp.data.repository.datasources

import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.repository.local.SubtaskEntity
import io.reactivex.Flowable

interface SubtaskDataSource {

    fun observeAllSubtasks(taskId: Long): Flowable<List<Subtask>>

    fun insertSubtask(subtaskEntity: Subtask)

    fun removeSubtask(subtaskEntity: Subtask)

    fun updateSubtask(subtaskEntity: Subtask)
}