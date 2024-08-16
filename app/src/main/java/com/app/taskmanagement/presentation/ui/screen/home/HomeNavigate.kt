package com.app.taskmanagement.presentation.ui.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_SCREEN = "home_screen"

fun NavGraphBuilder.homeScreen(
    onProjectClick: (Int) -> Unit
) {
    composable(route = HOME_SCREEN) {
        HomeScreen(onProjectClick = onProjectClick)
    }
}