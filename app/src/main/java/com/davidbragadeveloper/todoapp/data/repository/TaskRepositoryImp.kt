package com.davidbragadeveloper.todoapp.data.repository

import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.repository.local.LocalDataSource
import com.davidbragadeveloper.todoapp.data.repository.local.TodoDatabase
import io.reactivex.Flowable
import io.reactivex.Single

class TaskRepositoryImp (val localDataSource: LocalDataSource) : TaskRepository {

    override fun getAllTasks(): Single<List<Task>> = localDataSource.getAllTasks()

    override fun observerAll(): Flowable<List<Task>> = localDataSource.observerAll()

    override fun getTaskById(id: Long): Single<Task> = localDataSource.getTaskById(id)


    override fun insert(task: Task) {
        localDataSource.insert(task)
    }

    override fun delete(task: Task) {
        localDataSource.delete(task)
    }

    override fun update(task: Task) {
        localDataSource.update(task)
    }
}