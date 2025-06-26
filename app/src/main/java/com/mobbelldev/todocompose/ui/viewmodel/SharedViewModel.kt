package com.mobbelldev.todocompose.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobbelldev.todocompose.data.model.ToDoTask
import com.mobbelldev.todocompose.data.repository.ToDoRepository
import com.mobbelldev.todocompose.util.RequestState
import com.mobbelldev.todocompose.util.SearchBarAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
) : ViewModel() {

    val searchBarAppState: MutableState<SearchBarAppState> = mutableStateOf(
        value = SearchBarAppState.CLOSED
    )
    val searchTextState: MutableState<String> = mutableStateOf(value = "")

    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(value = RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(data = it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(error = e)
        }
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }
}