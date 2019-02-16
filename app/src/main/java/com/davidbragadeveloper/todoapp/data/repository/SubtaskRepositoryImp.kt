package com.davidbragadeveloper.todoapp.data.repository

import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.repository.SubtaskRepository
import com.davidbragadeveloper.todoapp.data.repository.local.LocalDataSource
import io.reactivex.Flowable

class SubtaskRepositoryImp (val localDataSource: LocalDataSource) : SubtaskRepository {
    override fun observeAllSubtasks(taskId: Long): Flowable<List<Subtask>> = localDataSource.observeAllSubtasks(taskId)

    override fun insertSubtask(subtaskEntity: Subtask) = localDataSource.insertSubtask(subtaskEntity)

    override fun removeSubtask(subtaskEntity: Subtask) = localDataSource.removeSubtask(subtaskEntity)

    override fun updateSubtask(subtaskEntity: Subtask) = localDataSource.updateSubtask(subtaskEntity)

}