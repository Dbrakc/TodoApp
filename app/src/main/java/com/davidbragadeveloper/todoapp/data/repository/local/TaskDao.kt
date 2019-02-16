package com.davidbragadeveloper.todoapp.data.repository.local

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class TaskDao {

    @Query("SELECT * FROM tasks ORDER BY is_high_priority AND created_at DESC")
    abstract fun getAll():Single<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY is_high_priority AND created_at DESC")
    abstract fun observeAll():Flowable<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    abstract fun getById(id: Long):Flowable<TaskEntity>

    @Insert
    abstract fun insert(taskEntity: TaskEntity)

    @Delete
    abstract fun remove(taskEntity: TaskEntity)

    @Update
    abstract fun update(taskEntity: TaskEntity)


}