package com.app.taskmanagement.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.app.taskmanagement.presentation.ui.screen.home.HOME_SCREEN
import com.app.taskmanagement.presentation.ui.screen.home.homeScreen
import com.app.taskmanagement.presentation.ui.screen.projectdetail.PROJECT_DETAIL_SCREEN
import com.app.taskmanagement.presentation.ui.screen.projectdetail.projectDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_SCREEN) {
        homeScreen(
            onProjectClick = {
                navController.navigate("$PROJECT_DETAIL_SCREEN/${it}")
            }
        )
        projectDetailScreen()
    }
}
