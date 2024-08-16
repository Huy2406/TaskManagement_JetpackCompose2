package com.app.taskmanagement.presentation.ui.screen.projectdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.taskmanagement.domain.model.Task
import com.app.taskmanagement.presentation.ui.screen.mytasks.AlertDeleteDialog
import com.app.taskmanagement.presentation.ui.screen.taskadd.TaskAddDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    id: Int,
    viewModel: ProjectDetailViewModel = hiltViewModel()
) {
    val isShowDialog = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getProject(id)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isShowDialog.value = true
            }) {
                Icon(imageVector = Icons.Rounded.AddTask, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is ProjectDetailState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 192.dp)
                        .align(Alignment.CenterHorizontally)
                )
                is ProjectDetailState.Success -> Content(
                    projectId = id,
                    state = state as ProjectDetailState.Success,
                    viewModel
                )
            }
        }
    }

    if (isShowDialog.value) {
        TaskAddDialog(
            task = null,
            projectId = id,
            isShowDialog = isShowDialog,
            updateProjectDate = {
                viewModel.updateProject()
            }
        )
    }
}

@Composable
private fun ColumnScope.Content(
    projectId: Int,
    state: ProjectDetailState.Success,
    viewModel: ProjectDetailViewModel
) {
    val isShowDialogConfirm = remember { mutableStateOf(false) }
    val isShowDialog = remember { mutableStateOf(false) }
    var taskSelected by remember {
        mutableStateOf<Task?>(null)
    }
    Text(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp),
        text = state.project.title,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )

    Text(
        modifier = Modifier.padding(horizontal = 32.dp),
        text = state.project.description,
        fontSize = 22.sp,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.height(24.dp))

    if (state.project.tasks.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier,
                text = "This project has no tasks yet.",
                fontSize = 16.sp
            )
        }
    } else {
        LazyColumn(modifier = Modifier.padding(horizontal = 24.dp)) {
            item {
                Text(
                    text = "List Task: ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            items(state.project.tasks.size) { index ->
                val task = state.project.tasks[index]

                ItemTask(
                    task = task,
                    onClickDelete = {
                        taskSelected = task
                        isShowDialogConfirm.value = true
                    },
                    onClickEdit = {
                        taskSelected = task
                        isShowDialog.value = true
                    },
                    onCheckedChange = {
                        viewModel.updateStatusTask(task.copy(done = it))
                    }
                )
            }
        }

        if (isShowDialogConfirm.value) {
            AlertDeleteDialog(isShowDialog = isShowDialogConfirm, onConfirm = {
                taskSelected?.let { viewModel.deleteTask(it.id) }
                viewModel.updateProject()
            })
        }

        if (isShowDialog.value) {
            TaskAddDialog(
                task = taskSelected,
                projectId = projectId,
                isShowDialog = isShowDialog,
                updateProjectDate = {
                    viewModel.updateProject()
                }
            )
        }
    }
}
