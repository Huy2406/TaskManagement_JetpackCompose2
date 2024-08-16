package com.app.taskmanagement.presentation.ui.screen.projectdetail

import com.app.taskmanagement.domain.model.Project

sealed class ProjectDetailState {
    data object Loading : ProjectDetailState()

    data class Success(
        val project: Project
    ): ProjectDetailState()
}