package com.app.taskmanagement.presentation.ui.screen.mytasks

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddTask
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.taskmanagement.domain.model.Project
import com.app.taskmanagement.presentation.compose.rememberKeyboardOpen
import com.app.taskmanagement.presentation.ui.screen.projectadd.ProjectAddDialog

private const val TAG = "MyTaskScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTaskScreen(
    modifier: Modifier = Modifier,
    onProjectClick: (Int) -> Unit,
    viewModel: MyTasksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by rememberKeyboardOpen()
    if (!isKeyboardOpen) {
        focusManager.clearFocus()
    }

    val showProjectAdd = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = "Good Morning") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showProjectAdd.value = true
            }) {
                Icon(imageVector = Icons.Rounded.AddTask, contentDescription = null)
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is MyTasksState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 192.dp)
                        .align(Alignment.CenterHorizontally)
                )
                is MyTasksState.Success -> Content(
                    state = state as MyTasksState.Success,
                    onSearch = {
                        viewModel.search(it)
                    },
                    onProjectClick = {onProjectClick(it)}
                )
            }
        }

        if (showProjectAdd.value) {
            ProjectAddDialog(
                project = null,
                isShowDialog = showProjectAdd,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnScope.Content(
    state: MyTasksState.Success,
    onSearch: (String) -> Unit,
    onProjectClick: (Int) -> Unit,
    viewModel: MyTasksViewModel = hiltViewModel()
) {
    val showProjectAdd = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val isShowDialog = remember { mutableStateOf(false) }
    val isSearching: Boolean = state.isSearching
    var searchText by remember { mutableStateOf("") }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchText,
                onQueryChange = {
                    Log.d(TAG, "onQueryChange()... $it")
                    searchText = it
                    onSearch(it)
                },
                onSearch = {
                    Log.d(TAG, "onSearch()... $it")
                    focusManager.clearFocus()
                },
                expanded = false,
                onExpandedChange = {},
                placeholder = {
                    Text(text = "Search...")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = {
                            searchText = ""
                            onSearch("")
                        }) {
                            Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                        }
                    }
                }
            )
        },
        expanded = false,
        onExpandedChange = {},
        modifier = Modifier.align(Alignment.CenterHorizontally),
        windowInsets = WindowInsets(0, 0, 0, 0),
        content = {}
    )

    if (isSearching) {
        val totalSearchProjects = state.searchProjects
        var projectEdit: Project? by remember { mutableStateOf(null) }
        if (totalSearchProjects.isNullOrEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier,
                    text = "No suitable project found",
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
                items(totalSearchProjects.size) { index ->
                    CardItemProject(totalSearchProjects[index],
                        onClickDelete = {
                            projectEdit = totalSearchProjects[index]
                            isShowDialog.value = true
                        }, onClickEdit = {
                            showProjectAdd.value = true
                            projectEdit = totalSearchProjects[index]
                        },
                        onCardClick = {onProjectClick(totalSearchProjects[index].id)}
                    )
                }
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
                isShowDialog = showProjectAdd
            )
        }
    } else {
        val totalProjects = state.totalProjects
        if (totalProjects.isEmpty()) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.60F)
            ) {
                Text(
                    text = "Empty Project.\nPress + to create new project.",
                    modifier = Modifier
                        .padding(top = 192.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val inProgressProjects = state.inProgressProjects
            var projectEdit: Project? by remember { mutableStateOf(null) }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalItemSpacing = 8.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                if (inProgressProjects.isNotEmpty()) {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = "In progress",
                            modifier = Modifier.padding(top = 24.dp),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    item(span = StaggeredGridItemSpan.FullLine) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(inProgressProjects.size) { index ->
                                CardItemProject(inProgressProjects[index],
                                    onClickDelete = {
                                        projectEdit = inProgressProjects[index]
                                        isShowDialog.value = true
                                    }, onClickEdit = {
                                        showProjectAdd.value = true
                                        projectEdit = inProgressProjects[index]
                                    },
                                    onCardClick = {onProjectClick(inProgressProjects[index].id)}
                                )
                            }
                        }
                    }
                }


                item(span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = "Total Projects",
                        modifier = Modifier.padding(top = 24.dp),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                items(totalProjects.size) { index ->
                    CardItemProject(totalProjects[index],
                        onClickDelete = {
                            projectEdit = totalProjects[index]
                            isShowDialog.value = true
                        }, onClickEdit = {
                            showProjectAdd.value = true
                            projectEdit = totalProjects[index]
                        },
                        onCardClick = {
                            onProjectClick(totalProjects[index].id)
                        }
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
                    isShowDialog = showProjectAdd
                )
            }
        }
    }
}
