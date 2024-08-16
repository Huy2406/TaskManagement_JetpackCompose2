package com.app.taskmanagement.data.di

import com.app.taskmanagement.data.repository.ProjectRepositoryImpl
import com.app.taskmanagement.data.repository.TaskRepositoryImpl
import com.app.taskmanagement.domain.repository.ProjectRepository
import com.app.taskmanagement.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProjectRepository(impl: ProjectRepositoryImpl): ProjectRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}
