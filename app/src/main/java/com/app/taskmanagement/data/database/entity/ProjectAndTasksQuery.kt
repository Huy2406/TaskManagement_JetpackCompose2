package com.app.taskmanagement.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectAndTasksQuery(
    @Embedded
    val project: ProjectEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "project_id"
    )
    val tasks: List<TaskEntity>
)
