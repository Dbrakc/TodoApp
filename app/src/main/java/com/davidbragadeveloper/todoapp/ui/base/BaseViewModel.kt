package com.davidbragadeveloper.todoapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidbragadeveloper.todoapp.data.model.Taskeable
import com.davidbragadeveloper.todoapp.ui.TaskViewModel
import com.davidbragadeveloper.todoapp.util.Event
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseViewModel : ViewModel(){


    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}