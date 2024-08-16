package com.app.taskmanagement.presentation.ui.screen.projectdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.taskmanagement.domain.model.Project
import com.app.taskmanagement.domain.model.Task
import com.app.taskmanagement.domain.repository.ProjectRepository
import com.app.taskmanagement.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel
@Inject
constructor(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProjectDetailState>(ProjectDetailState.Loading)
    val state: StateFlow<ProjectDetailState> = _state.asStateFlow()

    var project: Project? = null

    fun getProject(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.getProject(id).collectLatest {
                _state.value = ProjectDetailState.Success(it)
                project = it
            }
        }
    }

    fun updateProject() {
        viewModelScope.launch(Dispatchers.IO) {
            project?.copy(updatedDate = LocalDateTime.now())?.let { projectRepository.insert(it) }
        }
    }

    fun updateStatusTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insert(task)
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.delete(id)
        }
    }
}
