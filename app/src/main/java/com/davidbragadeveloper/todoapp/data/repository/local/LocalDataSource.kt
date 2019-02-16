package com.davidbragadeveloper.todoapp.data.repository.local

import android.util.Log
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.model.mapper.SubtaskEntityMapper
import com.davidbragadeveloper.todoapp.data.model.mapper.SubtaskMapper
import com.davidbragadeveloper.todoapp.data.model.mapper.TaskEntityMapper
import com.davidbragadeveloper.todoapp.data.model.mapper.TaskMapper
import com.davidbragadeveloper.todoapp.data.repository.datasources.SubtaskDataSource
import com.davidbragadeveloper.todoapp.data.repository.datasources.TaskDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSource (
    val todoDatabase: TodoDatabase
) : TaskDataSource, SubtaskDataSource {
    override fun getAllTasks(): Single<List<Task>> =
        todoDatabase
            .getTaskDao()
            .getAll()
            .map { TaskMapper.transformList(it)}

    override fun observerAllTasks(): Flowable<List<Task>>  =
        todoDatabase
            .getTaskDao()
            .observeAll()
            .map { TaskMapper.transformList(it) }

    override fun getTaskById(id: Long): Flowable<Task> =
        todoDatabase
            .getTaskDao()
            .getById(id)
            .map { TaskMapper.transform(it) }

    override fun insertTask(task: Task) {
        todoDatabase.getTaskDao().insert(TaskEntityMapper.transform(task))
    }


    override fun deleteTask(task: Task) {
        todoDatabase.getTaskDao().remove(TaskEntityMapper.transform(task))
    }

    override fun updateTask(task: Task) {
        todoDatabase.getTaskDao().update(TaskEntityMapper.transform(task))
    }

    override fun observeAllSubtasks(taskId: Long): Flowable<List<Subtask>> =
        todoDatabase
            .getSubtaskDao()
            .observeAll(taskId)
            .map {
                Log.d("Local data source response", it.size.toString())
                SubtaskMapper.transformList(it) }


    override fun insertSubtask(subtaskEntity: Subtask) {
        todoDatabase
            .getSubtaskDao()
            .insert(SubtaskEntityMapper.transform(subtaskEntity))
    }

    override fun removeSubtask(subtaskEntity: Subtask) {
        todoDatabase
            .getSubtaskDao()
            .remove(SubtaskEntityMapper.transform(subtaskEntity))
    }

    override fun updateSubtask(subtaskEntity: Subtask) {
        todoDatabase
            .getSubtaskDao()
            .update(SubtaskEntityMapper.transform(subtaskEntity))
    }




}
