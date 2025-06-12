package com.mobbelldev.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mobbelldev.todocompose.navigation.Screens
import com.mobbelldev.todocompose.ui.screens.list.ListScreen
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    composable<Screens.List> {
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel,
        )
    }
}