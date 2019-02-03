package com.davidbragadeveloper.todoapp.data.repository

import com.davidbragadeveloper.todoapp.data.model.Task
import io.reactivex.Flowable
import io.reactivex.Single


interface TaskRepository {

    fun getAllTasks():Single<List<Task>>

    fun observerAll(): Flowable<List<Task>>

    fun getTaskById(id: Long):Flowable<Task>

    fun insert(task: Task)

    fun delete (task: Task)

    fun update (task: Task)
}