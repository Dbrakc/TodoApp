package com.davidbragadeveloper.todoapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.model.Taskeable
import com.davidbragadeveloper.todoapp.data.repository.SubtaskRepository
import com.davidbragadeveloper.todoapp.ui.base.BaseViewModel
import com.davidbragadeveloper.todoapp.util.Event
import com.davidbragadeveloper.todoapp.util.call
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class SubtaskViewModel (val subtaskRepository: SubtaskRepository): BaseViewModel() {

    val subtaskLoadEvent = MutableLiveData<List<Taskeable>>()
    val subtaskAddedEvent = MutableLiveData<Event<Task>>()
    val subtaskUpdateEvent = MutableLiveData<Event<Taskeable>>()



    fun load(id:Long?) {
        id?.let {
            subtaskRepository
                .observeAllSubtasks(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        Log.d("event send", it.size.toString())
                        subtaskLoadEvent.value = it
                    },
                    onError = {
                        Log.e(TaskViewModel::class.java.simpleName, "Error: $it")

                    }
                ).addTo(compositeDisposable)
        }
    }

    fun addNew(content: String, isHighPriority: Boolean, task: Task) {
        val newSubtask = Subtask(0, content, Date(), false, isHighPriority, task.id)
        Completable.fromCallable {
            subtaskRepository.insertSubtask(newSubtask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    subtaskAddedEvent.call(task)
                },
                onError = {
                    Log.e("SubTaskViewModel", it.localizedMessage)
                }
            ).addTo(compositeDisposable)

    }


    fun update(new: Taskeable) {
        val subtask = new as Subtask
        Completable.fromCallable {
            subtaskRepository.updateSubtask(subtask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    subtaskUpdateEvent.call(subtask)
                },
                onError = {
                    Log.e("TextViewModel", it.localizedMessage)

                }
            ).addTo(compositeDisposable)
    }

    fun delete(subtask: Subtask) {
        Completable.fromCallable {
            subtaskRepository.removeSubtask(subtask)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    load(subtask.taskId)
                },
                onError = {
                    Log.e("TextViewModel", it.localizedMessage)

                }
            ).addTo(compositeDisposable)
    }

    fun markAsDone(subtask: Subtask) {
        if (subtask.isDone) {
            return
        }

        val newTask = subtask.copy(isDone = true)

        update(newTask)

    }

    fun markAsNotDone(subtask: Subtask) {
        if (!subtask.isDone) {
            return
        }

        val newTask = subtask.copy(isDone = false)

        update(newTask)

    }

    fun markAsHighPriority(subtask: Subtask, highPriority: Boolean) {
        val newTask = subtask.copy(isHighPriority = highPriority)
        update(newTask)
    }


}