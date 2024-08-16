package com.app.taskmanagement.presentation.ui.screen.taskadd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.taskmanagement.domain.model.Task

@Composable
fun TaskAddDialog(
    task: Task?,
    projectId: Int,
    isShowDialog: MutableState<Boolean>,
    updateProjectDate: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (task != null) {
            viewModel.initTask(task)
        }
    }
    AlertDialog(
        onDismissRequest = {
            viewModel.clearData()
            isShowDialog.value = false
        },
        title = {
            val textValue = if (task == null) "Edit Task" else "Add Task"
            Text(text = textValue)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = {
                        viewModel.updateTitle(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Title") },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                OutlinedTextField(
                    value = state.description,
                    onValueChange = {
                        viewModel.updateDescription(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Description") },
                    maxLines = 2,
                    shape = MaterialTheme.shapes.large
                )
            }
        },
        confirmButton = {
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Button(
                    onClick = {
                        isShowDialog.value = false
                        viewModel.clearData()
                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        isShowDialog.value = false
                        if (task == null) {
                            viewModel.addNewTask(projectId)
                        } else {
                            viewModel.updateTask(task)
                        }
                        updateProjectDate()
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = Color.White
                    )
                }
            }
        })
}
