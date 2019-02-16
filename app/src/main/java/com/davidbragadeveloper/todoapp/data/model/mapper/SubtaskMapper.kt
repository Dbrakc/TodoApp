package com.davidbragadeveloper.todoapp.data.model.mapper

import com.davidbragadeveloper.todoapp.data.model.HighPriority
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.repository.local.SubtaskEntity

object SubtaskMapper : Mapper<SubtaskEntity, Subtask> {
    override fun transform(input: SubtaskEntity): Subtask = Subtask(
        input.id,
        input.content,
        input.createdAt,
        input.isDone,
        HighPriority.valueOf(input.highPriority),
        input.taskId
    )

    override fun transformList(input: List<SubtaskEntity>): List<Subtask> = input.map { transform(it) }
}