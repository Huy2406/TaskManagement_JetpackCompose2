package com.app.taskmanagement.presentation.ui.screen.completedtasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.taskmanagement.domain.model.Project
import com.app.taskmanagement.presentation.ui.screen.mytasks.AlertDeleteDialog
import com.app.taskmanagement.presentation.ui.screen.mytasks.CardItemProject
import com.app.taskmanagement.presentation.ui.screen.projectadd.ProjectAddDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTasksScreen(
    modifier: Modifier = Modifier,
    viewModel: CompletedTasksViewModel = hiltViewModel(),
    onProjectClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Completed Tasks") }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            when (state) {
                is CompletedTaskState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 192.dp)
                        .align(Alignment.CenterHorizontally)
                )
                is CompletedTaskState.Success -> Content(
                    state = state as CompletedTaskState.Success,
                    onProjectClick = onProjectClick,
                    viewModel
                )
            }
        }
    }
}

@Composable
private fun Content(
    state: CompletedTaskState.Success,
    onProjectClick: (Int) -> Unit,
    viewModel: CompletedTasksViewModel
) {
    val isShowDialog = remember { mutableStateOf(false) }
    val totalCompletedProjects = state.completedProject
    val showProjectAdd = remember { mutableStateOf(false) }
    var projectEdit: Project? by remember { mutableStateOf(null) }
    if (totalCompletedProjects.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier,
                text = "No projects have been completed yet",
                fontSize = 16.sp
            )
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalItemSpacing = 8.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Spacer(modifier = Modifier.height(24.dp))
            }
            items(totalCompletedProjects.size) { index ->
                CardItemProject(totalCompletedProjects[index],
                    onClickDelete = {
                        projectEdit = totalCompletedProjects[index]
                        isShowDialog.value = true
                    }, onClickEdit = {
                        showProjectAdd.value = true
                        projectEdit = totalCompletedProjects[index]
                    },
                    onCardClick = { onProjectClick(totalCompletedProjects[index].id) }
                )
            }
        }

        if (isShowDialog.value) {
            AlertDeleteDialog(isShowDialog = isShowDialog, onConfirm = {
                viewModel.deleteProject(projectEdit!!)
            })
        }

        if (showProjectAdd.value) {
            ProjectAddDialog(
                project = projectEdit,
                isShowDialog = showProjectAdd,
            )
        }
    }
}
