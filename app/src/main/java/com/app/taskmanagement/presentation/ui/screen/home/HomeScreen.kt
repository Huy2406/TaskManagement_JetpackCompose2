package com.app.taskmanagement.presentation.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.taskmanagement.presentation.ui.screen.completedtasks.CompletedTasksScreen
import com.app.taskmanagement.presentation.ui.screen.mytasks.MyTaskScreen


@Composable
fun HomeScreen(
    onProjectClick: (Int) -> Unit
) {
    var screenIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (screenIndex) {
            0 -> MyTaskScreen(
                modifier = Modifier.weight(1F),
                onProjectClick = onProjectClick
            )
            1 -> CompletedTasksScreen(
                modifier = Modifier.weight(1F),
                onProjectClick = onProjectClick
            )
        }
        NavigationBar {
            NavigationBarItem(
                selected = screenIndex == 0,
                onClick = { screenIndex = 0 },
                icon = { Icon(imageVector = Icons.Rounded.Checklist, contentDescription = null) },
                label = { Text(text = "My Tasks") }
            )
            NavigationBarItem(
                selected = screenIndex == 1,
                onClick = { screenIndex = 1 },
                icon = { Icon(imageVector = Icons.Rounded.TaskAlt, contentDescription = null) },
                label = { Text(text = "Completed Tasks") }
            )
        }
    }
}
