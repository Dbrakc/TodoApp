package com.davidbragadeveloper.todoapp.data.repository.local

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class SubtaskDao {

    @Query("SELECT * FROM subtasks WHERE task_id=:taskId ORDER BY is_high_priority AND created_at DESC")
    abstract fun observeAll(taskId: Long): Flowable<List<SubtaskEntity>>

    /*@Query("SELECT * FROM tasks WHERE id = :id")
    abstract fun getById(id: Long): Flowable<TaskEntity>*/

    @Insert
    abstract fun insert(subtaskEntity: SubtaskEntity)

    @Delete
    abstract fun remove(subtaskEntity: SubtaskEntity)

    @Update
    abstract fun update(subtaskEntity: SubtaskEntity)

}
