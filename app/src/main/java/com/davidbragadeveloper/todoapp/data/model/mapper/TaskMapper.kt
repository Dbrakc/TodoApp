package com.davidbragadeveloper.todoapp.data.model.mapper

import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.repository.local.TaskEntity
import java.util.*

class TaskMapper: Mapper<TaskEntity, Task> {
    override fun transform(input: TaskEntity): Task = Task(
        input.id,
        input.content,
        input.createdAt,
        input.isDone,
        input.isHeighPriority
       )


    override fun transformList(input: List<TaskEntity>): List<Task> = input.map { transform(it) }

}