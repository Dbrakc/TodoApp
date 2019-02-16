package com.davidbragadeveloper.todoapp.data.repository

import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.repository.local.LocalDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class TaskRepositoryImp (val localDataSource: LocalDataSource) : TaskRepository {

    override fun getAllTasks(): Single<List<Task>> = localDataSource.getAllTasks()

    override fun observerAll(): Flowable<List<Task>> = localDataSource.observerAllTasks()

    override fun getTaskById(id: Long): Flowable<Task> = localDataSource.getTaskById(id)


    override fun insert(task: Task) {
        localDataSource.insertTask(task)
    }

    override fun delete(task: Task) {
        localDataSource.deleteTask(task)
    }

    override fun update(task: Task) {
        localDataSource.updateTask(task)
    }
}