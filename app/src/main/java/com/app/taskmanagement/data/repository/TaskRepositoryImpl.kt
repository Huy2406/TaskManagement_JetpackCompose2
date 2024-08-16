package com.app.taskmanagement.data.repository

import com.app.taskmanagement.data.database.dao.TaskDao
import com.app.taskmanagement.data.mapper.toEntity
import com.app.taskmanagement.domain.model.Task
import com.app.taskmanagement.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl
@Inject
constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override suspend fun insert(model: Task) = taskDao.insert(model.toEntity())

    override suspend fun delete(id: Int) = taskDao.delete(id)
}
