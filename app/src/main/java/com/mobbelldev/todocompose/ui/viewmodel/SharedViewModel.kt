package com.mobbelldev.todocompose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobbelldev.todocompose.data.model.Priority
import com.mobbelldev.todocompose.data.model.ToDoTask
import com.mobbelldev.todocompose.data.repository.ToDoRepository
import com.mobbelldev.todocompose.util.Action
import com.mobbelldev.todocompose.util.Constants.MAX_TITLE_LENGTH
import com.mobbelldev.todocompose.util.RequestState
import com.mobbelldev.todocompose.util.SearchBarAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
) : ViewModel() {

    var action: Action by mutableStateOf(value = Action.NO_ACTION)
        private set

    var id: Int by mutableIntStateOf(value = 0)
        private set
    var title: String by mutableStateOf(value = "")
        private set
    var description: String by mutableStateOf(value = "")
        private set
    var priority: Priority by mutableStateOf(value = Priority.LOW)
        private set

    var searchBarAppState: SearchBarAppState by mutableStateOf(
        value = SearchBarAppState.CLOSED
    )
        private set

    var searchTextState: String by mutableStateOf(value = "")
        private set

    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(value = RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    init {
        getAllTasks()
    }

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

    private val _searchedTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(value = RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(
                    searchQuery = "%$searchQuery%"
                ).collect { searchTasks ->
                    _searchedTasks.value = RequestState.Success(
                        data = searchTasks
                    )
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(
                error = e
            )
        }
        searchBarAppState = SearchBarAppState.TRIGGERED
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

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title,
                description = description,
                priority = priority
            )
            repository.addTask(
                toDoTask = toDoTask
            )
        }
        searchBarAppState = SearchBarAppState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.updateTask(
                toDoTask = toDoTask
            )
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.deleteTask(
                toDoTask = toDoTask
            )
        }
    }

    fun handleDatabaseAction(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
                updateAction(
                    newAction = Action.NO_ACTION
                )
            }

            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> {}
            Action.UNDO -> {
                addTask()
                updateAction(
                    newAction = Action.NO_ACTION
                )
            }

            Action.NO_ACTION -> {}
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id = selectedTask.id
            title = selectedTask.title
            description = selectedTask.description
            priority = selectedTask.priority
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    fun updateAppBarState(newState: SearchBarAppState) {
        searchBarAppState = newState
    }

    fun updateSearchText(newText: String) {
        searchTextState = newText
    }

    fun validateFields(): Boolean = title.isNotEmpty() && description.isNotEmpty()
}