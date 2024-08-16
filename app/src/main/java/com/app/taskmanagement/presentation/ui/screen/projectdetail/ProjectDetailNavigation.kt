package com.app.taskmanagement.presentation.ui.screen.projectdetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val PROJECT_DETAIL_SCREEN = "project_detail_screen"

fun NavGraphBuilder.projectDetailScreen() {
    composable(route = "$PROJECT_DETAIL_SCREEN/{id}") { it ->
        val id = it.arguments?.getString("id")
        if (id != null) {
            ProjectDetailScreen(
                id = id.toInt()
            )
        }
    }
}