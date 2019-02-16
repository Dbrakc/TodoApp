package com.davidbragadeveloper.todoapp.data.model.mapper

import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.repository.local.TaskEntity

object TaskMapper: Mapper<TaskEntity, Task> {
    override fun transform(input: TaskEntity): Task = Task(
        input.id,
        input.content,
        input.createdAt,
        input.isDone,
        input.isHighPriority
       )


    override fun transformList(input: List<TaskEntity>): List<Task> = input.map { transform(it) }

}