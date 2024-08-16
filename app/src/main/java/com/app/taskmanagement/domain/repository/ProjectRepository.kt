package com.app.taskmanagement.domain.repository

import com.app.taskmanagement.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun insert(model: Project)

    suspend fun delete(id: Int)

    fun getProjects(done: Boolean): Flow<List<Project>>

    fun getProject(id: Int): Flow<Project>
}
