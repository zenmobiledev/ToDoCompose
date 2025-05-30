package com.mobbelldev.todocompose.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobbelldev.todocompose.data.model.ToDoTask
import com.mobbelldev.todocompose.data.repository.ToDoRepository
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

    private val _allTasks = MutableStateFlow<List<ToDoTask>>(value = emptyList())
    val allTasks: StateFlow<List<ToDoTask>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }
}