package com.davidbragadeveloper.todoapp.ui.tasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.repository.TaskRepository
import com.davidbragadeveloper.todoapp.ui.base.BaseViewModel
import com.davidbragadeveloper.todoapp.util.Event
import com.davidbragadeveloper.todoapp.util.call
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class TaskViewModel (val taskRepository: TaskRepository) : BaseViewModel() {

    val tasksEvent = MutableLiveData<List<Task>>()
    val newTaskAddedEvent = MutableLiveData<Event<Unit>>()
    val updateTaskEvent = MutableLiveData<Event<Task>>()

    init {
        loadTasks()
    }

    fun loadTasks () {
        taskRepository
            .observerAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext= {
                    tasksEvent.value = it
                },
                onError = {
                    Log.e(TaskViewModel::class.java.simpleName, "Error: $it")

                }
            ).addTo(compositeDisposable)
    }

    fun addNewTask (taskContent: String){
        val newTask = Task (0,taskContent, Date(),false)
        Completable.fromCallable {
            taskRepository.insert(newTask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete={
                    loadTasks()
                    newTaskAddedEvent.call()
                },
                onError={
                    Log.e("TextViewModel", it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    fun markAsDone (task: Task) {
        if(task.isDone){
            return
        }

        val newTask = task.copy(isDone = true)

        updateTask(newTask)
    }



    fun markAsNotDone (task: Task){

        if(!task.isDone){
            return
        }

        val newTask = task.copy(isDone = false)

        updateTask(newTask)
    }

    fun updateTaskContent(task:Task, newContent: String){
        val newTask = task.copy(content = newContent)
        updateTask(newTask)
    }


    fun updateTask(newTask: Task) {
        Completable.fromCallable {
            taskRepository.update(newTask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    updateTaskEvent.call(newTask)
                },
                onError = {
                    Log.e("TextViewModel", it.localizedMessage)

                }
            ).addTo(compositeDisposable)
    }

    fun deleteTask(task: Task) {
        Completable.fromCallable {
            taskRepository.delete(task)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    loadTasks()
                },
                onError = {
                    Log.e("TextViewModel", it.localizedMessage)

                }
            ).addTo(compositeDisposable)

    }





}