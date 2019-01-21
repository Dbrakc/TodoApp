package com.davidbragadeveloper.todoapp.ui

import android.app.Application
import com.davidbragadeveloper.todoapp.di.modules.appModule
import com.facebook.stetho.Stetho

import org.koin.android.ext.android.startKoin


class TodoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, modules = listOf(appModule))
        Stetho.initializeWithDefaults(this)
    }

}