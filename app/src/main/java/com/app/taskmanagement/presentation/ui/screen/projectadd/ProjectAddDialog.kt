package com.app.taskmanagement.presentation.ui.screen.projectadd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.taskmanagement.domain.model.CategoryType
import com.app.taskmanagement.domain.model.Project
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectAddDialog(
    project: Project?,
    isShowDialog: MutableState<Boolean>,
    viewModel: ProjectAddViewModel = hiltViewModel()
) {
    val currentProject by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        project?.let {
            viewModel.initProject(it)
        }
    }
    var categoryExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        currentProject.dueDate.toInstant(ZoneOffset.UTC).toEpochMilli()
    )
    val selectedDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""

    AlertDialog(
        onDismissRequest = {
            viewModel.clearData()
            isShowDialog.value = false
        },
        confirmButton = {
            Button(
                onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis ?: return@Button
                    val selectedDateTime = selectedDateMillis.let {
                        Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                    }
                    viewModel.insertProject(selectedDateTime)
                    isShowDialog.value = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                val textValue = if (project == null) "Add" else "Save"
                Text(text = textValue)
            }
        },
        title = {
            val text = if (project == null) "Add Project" else "Edit Project"
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = currentProject.title,
                    onValueChange = {
                        viewModel.updateTitle(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Title") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )
                OutlinedTextField(
                    value = currentProject.description,
                    onValueChange = {
                        viewModel.updateDescription(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Description") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 2,
                    shape = MaterialTheme.shapes.large
                )
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it },
                ) {
                    OutlinedTextField(
                        value = currentProject.categoryType.text,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        singleLine = true,
                        shape = MaterialTheme.shapes.large,
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false },
                        shape = MaterialTheme.shapes.large
                    ) {
                        CategoryType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.text) },
                                onClick = {
                                    viewModel.updateCategory(type)
                                    categoryExpanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    label = { Text("Due Date") },
                    trailingIcon = {
                        IconButton(onClick = {
                            showDatePicker = !showDatePicker
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.EditCalendar,
                                contentDescription = null
                            )
                        }
                    },
                    shape = MaterialTheme.shapes.large,
                )
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showDatePicker = false
                            }) {
                                Text("Select")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
        }
    )

}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}