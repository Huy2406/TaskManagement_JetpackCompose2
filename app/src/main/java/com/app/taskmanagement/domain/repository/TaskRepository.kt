package com.app.taskmanagement.domain.repository

import com.app.taskmanagement.domain.model.Task

interface TaskRepository {
    suspend fun insert(model: Task)

    suspend fun delete(id: Int)
}
