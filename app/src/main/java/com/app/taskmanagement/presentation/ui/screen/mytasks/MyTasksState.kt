package com.app.taskmanagement.presentation.ui.screen.mytasks

import com.app.taskmanagement.domain.model.Project

sealed class MyTasksState {
    data object Loading : MyTasksState()

    data class Success(
        val isSearching: Boolean = false,
        val inProgressProjects: List<Project> = listOf(),
        val totalProjects: List<Project> = listOf(),
        val searchProjects: List<Project>? = null
    ) : MyTasksState()
}
