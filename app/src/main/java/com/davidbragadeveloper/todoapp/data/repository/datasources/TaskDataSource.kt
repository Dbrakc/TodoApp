package com.davidbragadeveloper.todoapp.data.repository.datasources

import com.davidbragadeveloper.todoapp.data.model.Task
import io.reactivex.Flowable
import io.reactivex.Single

interface TaskDataSource {

    fun getAllTasks(): Single<List<Task>>

    fun observerAllTasks(): Flowable<List<Task>>

    fun getTaskById(id: Long): Flowable<Task>

    fun insertTask(task: Task)

    fun deleteTask (task: Task)

    fun updateTask (task: Task)

}