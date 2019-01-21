package com.davidbragadeveloper.todoapp.util

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Event<Unit>>.call(){
    value = Event(Unit)
}

fun <T> MutableLiveData<Event<T>>.call(input: T){
    value = Event(input)
}