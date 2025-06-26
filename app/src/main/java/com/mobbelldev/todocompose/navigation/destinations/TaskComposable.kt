package com.mobbelldev.todocompose.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mobbelldev.todocompose.navigation.Screen
import com.mobbelldev.todocompose.ui.screens.task.TaskScreen
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel
import com.mobbelldev.todocompose.util.Action

fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit,
) {
    composable<Screen.Task> { navBackStackEntry ->
        val taskId = navBackStackEntry.toRoute<Screen.Task>().id
        sharedViewModel.getSelectedTask(
            taskId = taskId
        )

        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            selectedTask = selectedTask
        )
    }
}