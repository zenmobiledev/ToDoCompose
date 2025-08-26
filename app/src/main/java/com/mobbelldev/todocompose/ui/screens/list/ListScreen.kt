package com.mobbelldev.todocompose.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mobbelldev.todocompose.R
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel
import com.mobbelldev.todocompose.util.Action
import com.mobbelldev.todocompose.util.SearchBarAppState

@Composable
fun ListScreen(
    action: Action,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
//    LaunchedEffect(key1 = true) {
//        sharedViewModel.handleDatabaseAction(
//            action = action
//        )
//    }
    LaunchedEffect(key1 = action) {
        sharedViewModel.getAllTasks()
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchTasks by sharedViewModel.searchedTasks.collectAsState()

    val searchBarAppState: SearchBarAppState = sharedViewModel.searchBarAppState
    val searchTextState: String = sharedViewModel.searchTextState

    val snackBarHostState = remember { SnackbarHostState() }

    sharedViewModel.handleDatabaseAction(
        action = action
    )
    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        onComplete = {
            sharedViewModel.updateAction(
                newAction = it
            )
        },
        onUndoClicked = {
            sharedViewModel.updateAction(
                newAction = it
            )
        },
        taskTitle = sharedViewModel.title,
        action = action
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchBarAppState = searchBarAppState,
                searchTextState = searchTextState
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
            ) {
                ListContent(
                    allTasks = allTasks,
                    searchedTasks = searchTasks,
                    searchBarAppState = searchBarAppState,
                    navigateToTaskScreen = navigateToTaskScreen,

                    )
            }

        },
        floatingActionButton = {
            ListFAB(
                onFabClicked = navigateToTaskScreen
            )
        },
    )
}

@Composable
fun ListFAB(
    onFabClicked: (taskId: Int) -> Unit,
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
        )
    }
}

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action,
) {
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            val snackBarResult: SnackbarResult = snackBarHostState.showSnackbar(
                message = setMessage(action = action, taskTitle = taskTitle),
                actionLabel = setActionLabel(action = action),
                duration = SnackbarDuration.Short
            )
            if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
                onUndoClicked(Action.UNDO)
            } else if (snackBarResult == SnackbarResult.Dismissed || action != Action.DELETE) {
                onComplete(Action.NO_ACTION)
            }
        }
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Tasks Removed"
        else -> "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}