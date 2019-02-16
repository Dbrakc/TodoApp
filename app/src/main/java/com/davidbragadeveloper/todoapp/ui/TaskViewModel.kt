package com.davidbragadeveloper.todoapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.model.Taskeable
import com.davidbragadeveloper.todoapp.data.repository.TaskRepository
import com.davidbragadeveloper.todoapp.ui.base.BaseViewModel
import com.davidbragadeveloper.todoapp.util.Event
import com.davidbragadeveloper.todoapp.util.call
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class TaskViewModel(val taskRepository: TaskRepository) : BaseViewModel() {

    val loadEvent = MutableLiveData<List<Task>>()
    val addedEvent = MutableLiveData<Event<Unit>>()
    val updateEvent = MutableLiveData<Event<Task>>()
    val taskByIdEvent = MutableLiveData<Task>()



    init {
        load()
    }

    fun load() {
        taskRepository
            .observerAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    loadEvent.value = it
                },
                onError = {
                    Log.e(TaskViewModel::class.java.simpleName, "Error: $it")

                }
            ).addTo(compositeDisposable)
    }

    fun addNew(content: String, isHighPriority: Boolean) {
        val newTask = Task(0, content, Date(), false, isHighPriority)
        Completable.fromCallable {
            taskRepository.insert(newTask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    addedEvent.call()
                },
                onError = {
                    Log.e("TextViewModel", it.localizedMessage)
                }
            ).addTo(compositeDisposable)
    }

    fun getById(id: Long) {
        taskRepository
            .getTaskById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    taskByIdEvent.value = it
                },
                onError = {
                    Log.e(TaskViewModel::class.java.simpleName, "Error: $it")
                }
            ).addTo(compositeDisposable)
    }


    fun update(newTask: Task) {
            Completable.fromCallable {
                taskRepository.update(newTask)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        updateEvent.call(newTask)
                    },
                    onError = {
                        Log.e("TextViewModel", it.localizedMessage)

                    }
                ).addTo(compositeDisposable)

    }

    fun delete(task: Task) {

            Completable.fromCallable {
                taskRepository.delete(task)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        load()
                    },
                    onError = {
                        Log.e("TextViewModel", it.localizedMessage)

                    }
                ).addTo(compositeDisposable)

    }

    fun markAsDone(task: Task) {
            if (task.isDone) {
                return
            }

            val newTask = task.copy(isDone = true)

            update(newTask)
    }


    fun markAsNotDone(task: Task) {
            if (!task.isDone) {
                return
            }

            val newTask = task.copy(isDone = false)

            update(newTask)
    }


    fun markAsHighPriority(task: Task, highPriority: Boolean) {
            val newTask = task.copy(isHighPriority = highPriority)
            update(newTask)
    }



}