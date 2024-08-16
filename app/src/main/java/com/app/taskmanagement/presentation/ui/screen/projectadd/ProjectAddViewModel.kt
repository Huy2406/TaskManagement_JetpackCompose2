package com.app.taskmanagement.presentation.ui.screen.projectadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.taskmanagement.domain.model.CategoryType
import com.app.taskmanagement.domain.model.Project
import com.app.taskmanagement.domain.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ProjectAddViewModel
@Inject
constructor(
    private val projectRepository: ProjectRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(
        Project(
            title = "",
            description = "",
            categoryType = CategoryType.entries.first(),
            dueDate = LocalDateTime.now()
        )
    )
    val state: StateFlow<Project> = _state.asStateFlow()

    fun initProject(project: Project) {
        _state.value = project
    }

    fun insertProject(dueDate: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.insert(state.value.copy(dueDate = dueDate))
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(
                    title = "",
                    description = "",
                    categoryType = CategoryType.entries.first(),
                    dueDate = LocalDateTime.now()
                )
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _state.value = _state.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _state.value = _state.value.copy(description = newDescription)
    }

    fun updateCategory(newCategory: CategoryType) {
        _state.value = _state.value.copy(categoryType = newCategory)
    }

    fun clearData() {
        _state.value = _state.value.copy(
            title = "",
            description = "",
            categoryType = CategoryType.entries.first(),
            dueDate = LocalDateTime.now()
        )
    }
}
