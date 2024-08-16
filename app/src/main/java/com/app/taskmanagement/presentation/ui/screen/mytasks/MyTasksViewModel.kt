package com.app.taskmanagement.presentation.ui.screen.mytasks

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
class MyTasksViewModel
@Inject
constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private val _state = MutableStateFlow<MyTasksState>(MyTasksState.Loading)
    val state: StateFlow<MyTasksState> = _state.asStateFlow()

    private var searchText = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.getProjects(false).collectLatest { projects ->
                val totalProjects = projects.filter { project ->
                    project.tasks.isEmpty() || project.tasks.any { !it.done }
                }
                val inProgressProjects = totalProjects.filter { project ->
                    project.tasks.any { it.done }
                }

                _state.value = MyTasksState.Success(
                    inProgressProjects = inProgressProjects,
                    totalProjects = totalProjects
                )
            }
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.delete(project.id)
        }
    }

    fun search(searchText: String) {
        if (searchText == this.searchText) return
        this.searchText = searchText

        val state = _state.value as MyTasksState.Success

        if (searchText.isEmpty()) {
            _state.value = state.copy(
                isSearching = false,
                searchProjects = null
            )
        } else {
            val searchProjects = state.totalProjects.filter { project ->
                val c1 = project.title.contains(searchText, ignoreCase = true)
                if (c1) return@filter true

                val c2 = project.description.contains(searchText, ignoreCase = true)
                if (c2) return@filter true

                project.tasks.any {
                    it.title.contains(searchText, ignoreCase = true) ||
                            it.description.contains(searchText, ignoreCase = true)
                }
            }

            _state.value = state.copy(
                isSearching = true,
                searchProjects = searchProjects
            )
        }
    }
}
