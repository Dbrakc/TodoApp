package com.davidbragadeveloper.todoapp.data.model.mapper

import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.data.repository.local.SubtaskEntity

object  SubtaskEntityMapper : Mapper<Subtask, SubtaskEntity> {
    override fun transform(input: Subtask): SubtaskEntity = SubtaskEntity(
        input.id,
        input.content,
        input.createdAt,
        input.isDone,
        input.highPriority.toString(),
        input.taskId

    )

    override fun transformList(input: List<Subtask>): List<SubtaskEntity> = input.map{transform(it)}
}