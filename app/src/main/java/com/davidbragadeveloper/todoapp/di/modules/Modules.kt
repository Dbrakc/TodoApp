package com.davidbragadeveloper.todoapp.di.modules

import androidx.room.Room
import com.davidbragadeveloper.todoapp.data.repository.SubtaskRepository
import com.davidbragadeveloper.todoapp.data.repository.SubtaskRepositoryImp
import com.davidbragadeveloper.todoapp.data.repository.TaskRepository
import com.davidbragadeveloper.todoapp.data.repository.TaskRepositoryImp
import com.davidbragadeveloper.todoapp.data.repository.local.LocalDataSource
import com.davidbragadeveloper.todoapp.data.repository.local.TodoDatabase
import com.davidbragadeveloper.todoapp.ui.SubtaskViewModel
import com.davidbragadeveloper.todoapp.ui.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {


    single { Room.databaseBuilder(androidContext(), TodoDatabase::class.java,"todo.db").build() }

    single { LocalDataSource( get() ) }

    single <TaskRepository> { TaskRepositoryImp( get() ) }

    single <SubtaskRepository> {SubtaskRepositoryImp ( get () )}

    viewModel { TaskViewModel(get()) }

    viewModel { SubtaskViewModel(get()) }



}