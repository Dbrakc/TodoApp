package com.davidbragadeveloper.todoapp.data.repository.local

import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.model.mapper.TaskEntityMapper
import com.davidbragadeveloper.todoapp.data.model.mapper.TaskMapper
import com.davidbragadeveloper.todoapp.data.repository.TaskDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSource (
    val todoDatabase: TodoDatabase,
    val taskMapper: TaskMapper,
    val taskEntityMapper: TaskEntityMapper
) : TaskDataSource {
    override fun getAllTasks(): Single<List<Task>> =
        todoDatabase
            .getTaskDao()
            .getAll()
            .map { taskMapper.transformList(it)}

    override fun observerAll(): Flowable<List<Task>>  =
        todoDatabase
            .getTaskDao()
            .observeAll()
            .map { taskMapper.transformList(it) }


    override fun getTaskById(id: Long): Single<Task> =
        todoDatabase
            .getTaskDao()
            .getById(id)
            .map { taskMapper.transform(it) }

    override fun insert(task: Task) {
        todoDatabase.getTaskDao().insert(taskEntityMapper.transform(task))
    }

    override fun delete(task: Task) {
        todoDatabase.getTaskDao().remove(taskEntityMapper.transform(task))
    }

    override fun update(task: Task) {
        todoDatabase.getTaskDao().update(taskEntityMapper.transform(task))
    }
}