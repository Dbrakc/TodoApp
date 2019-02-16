package com.davidbragadeveloper.todoapp.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskEntity::class, SubtaskEntity::class], version= 1)
@TypeConverters(com.davidbragadeveloper.todoapp.data.repository.local.TypeConverters::class)
abstract class TodoDatabase : RoomDatabase (){

    abstract fun getTaskDao():TaskDao

    abstract fun getSubtaskDao():SubtaskDao

}