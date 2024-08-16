package com.app.taskmanagement.data.mapper

import com.app.taskmanagement.data.database.entity.ProjectAndTasksQuery
import com.app.taskmanagement.data.database.entity.ProjectEntity
import com.app.taskmanagement.domain.model.Project

fun ProjectAndTasksQuery.toProject() = Project(
    project.id,
    project.updatedDate,
    project.title,
    project.description,
    project.categoryType,
    project.dueDate,
    project.done,
    tasks.map { it.toModel() }
)

fun Project.toEntity() = ProjectEntity(
    id,
    updatedDate,
    title,
    description,
    categoryType,
    dueDate,
    done
)
