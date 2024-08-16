package com.app.taskmanagement.data.mapper

import com.app.taskmanagement.data.database.entity.TaskEntity
import com.app.taskmanagement.domain.model.Task

fun TaskEntity.toModel() = Task(
    id,
    projectId,
    createdDate,
    title,
    description,
    done
)

fun Task.toEntity() = TaskEntity(
    id,
    projectId,
    createdDate,
    title,
    description,
    done
)
