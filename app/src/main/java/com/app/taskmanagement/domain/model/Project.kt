package com.app.taskmanagement.domain.model

import java.time.LocalDateTime

data class Project(
    val id: Int = 0,
    val updatedDate: LocalDateTime = LocalDateTime.now(),
    val title: String,
    val description: String,
    val categoryType: CategoryType,
    val dueDate: LocalDateTime,
    val done: Boolean = false,
    val tasks: List<Task> = arrayListOf()
)
