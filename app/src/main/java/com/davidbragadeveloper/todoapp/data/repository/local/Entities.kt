package com.davidbragadeveloper.todoapp.data.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean,
    @ColumnInfo(name = "is_high_priority")
    val isHighPriority: Boolean
)

@Entity (tableName = "subtasks",
    foreignKeys = arrayOf(ForeignKey(
        entity = TaskEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("task_id"),
        onDelete = ForeignKey.CASCADE)))

data class SubtaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean,
    @ColumnInfo(name = "is_high_priority")
    val isHighPriority: Boolean,
    @ColumnInfo(name = "task_id")
    val taskId: Long
)

