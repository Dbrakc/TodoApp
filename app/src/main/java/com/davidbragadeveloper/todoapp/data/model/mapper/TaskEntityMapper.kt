package com.davidbragadeveloper.todoapp.data.model.mapper

import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.repository.local.TaskEntity

object TaskEntityMapper: Mapper<Task, TaskEntity> {
    override fun transform(input: Task): TaskEntity = TaskEntity(
        input.id,
        input.content,
        input.createdAt,
        input.isDone,
        input.highPriority.toString()
    )

    override fun transformList(input: List<Task>): List<TaskEntity> =
        input.map { transform(it) }
}