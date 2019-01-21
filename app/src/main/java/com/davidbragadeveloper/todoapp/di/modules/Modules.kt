package com.davidbragadeveloper.todoapp.di.modules

import androidx.room.Room
import com.davidbragadeveloper.todoapp.data.model.mapper.TaskEntityMapper
import com.davidbragadeveloper.todoapp.data.model.mapper.TaskMapper
import com.davidbragadeveloper.todoapp.data.repository.TaskRepository
import com.davidbragadeveloper.todoapp.data.repository.TaskRepositoryImp
import com.davidbragadeveloper.todoapp.data.repository.local.LocalDataSource
import com.davidbragadeveloper.todoapp.data.repository.local.TodoDatabase
import com.davidbragadeveloper.todoapp.ui.tasks.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    single { TaskMapper() }

    single { TaskEntityMapper() }

    single { Room.databaseBuilder(androidContext(), TodoDatabase::class.java,"todo.db").build() }

    single { LocalDataSource( get(), get(), get() ) }

    single <TaskRepository> { TaskRepositoryImp( get() ) }

    viewModel{ TaskViewModel(get())}


}