package com.app.taskmanagement.presentation.ui.screen.completedtasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.taskmanagement.domain.model.Project
import com.app.taskmanagement.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedTasksViewModel
@Inject
constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private val _state = MutableStateFlow<CompletedTaskState>(CompletedTaskState.Loading)
    val state: StateFlow<CompletedTaskState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.getProjects(false).collectLatest { projects ->
                val completedProjects = projects.filter { project ->
                    project.tasks.isNotEmpty() && project.tasks.all { it.done }
                }
                _state.value = CompletedTaskState.Success(completedProjects)
            }
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.delete(project.id)
        }
    }
}
