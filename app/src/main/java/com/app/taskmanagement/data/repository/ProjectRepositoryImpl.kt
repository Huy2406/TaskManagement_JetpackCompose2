package com.app.taskmanagement.data.repository

import com.app.taskmanagement.data.database.dao.ProjectDao
import com.app.taskmanagement.data.mapper.toEntity
import com.app.taskmanagement.data.mapper.toProject
import com.app.taskmanagement.domain.model.Project
import com.app.taskmanagement.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProjectRepositoryImpl
@Inject
constructor(
    private val projectDao: ProjectDao
) : ProjectRepository {
    override suspend fun insert(model: Project) = projectDao.insert(model.toEntity())

    override suspend fun delete(id: Int) = projectDao.delete(id)

    override fun getProjects(done: Boolean) = projectDao
        .getProjectAndTasksQueries(false)
        .map { list -> list.map { it.toProject() } }

    override fun getProject(id: Int): Flow<Project> = projectDao
        .getProject(id)
        .map { it.toProject() }
}
