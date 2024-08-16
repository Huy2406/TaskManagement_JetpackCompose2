package com.app.taskmanagement.presentation.ui.screen.completedtasks

import com.app.taskmanagement.domain.model.Project

sealed class CompletedTaskState {
    data object Loading : CompletedTaskState()

    data class Success(
        val completedProject: List<Project> = listOf()
    ): CompletedTaskState()
}