package com.app.taskmanagement.presentation.ui.screen.taskadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.taskmanagement.domain.model.Task
import com.app.taskmanagement.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _state = MutableStateFlow(
        Task(
            projectId = 0,
            createdDate = LocalDateTime.now(),
            title = "",
            description = "",
            done = false
        )
    )
    val state: StateFlow<Task> = _state.asStateFlow()

    fun initTask(task: Task) {
        _state.value = task
    }

    fun updateTitle(newTitle: String) {
        _state.value = _state.value.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _state.value = _state.value.copy(description = newDescription)
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insert(_state.value.copy(id = task.id, projectId = task.projectId))
        }
    }

    fun addNewTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.insert(_state.value.copy(projectId = id))
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(
                    title = "",
                    description = "",
                )
            }
        }
    }

    fun clearData() {
        _state.value = _state.value.copy(
            title = "",
            description = ""
        )
    }
}