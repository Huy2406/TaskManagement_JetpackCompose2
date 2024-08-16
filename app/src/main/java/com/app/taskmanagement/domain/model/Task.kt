package com.app.taskmanagement.domain.model

import java.time.LocalDateTime

data class Task(
    val id: Int = 0,
    val projectId: Int,
    val createdDate: LocalDateTime,
    val title: String,
    val description: String,
    val done: Boolean
)
