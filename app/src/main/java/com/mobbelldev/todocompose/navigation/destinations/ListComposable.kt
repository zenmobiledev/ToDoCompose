package com.mobbelldev.todocompose.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mobbelldev.todocompose.navigation.Screen
import com.mobbelldev.todocompose.ui.screens.list.ListScreen
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel
import com.mobbelldev.todocompose.util.Action

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    composable<Screen.List> { navBackStackEntry ->
        val action: Action = navBackStackEntry.toRoute<Screen.List>().action
        var myAction by rememberSaveable { mutableStateOf(value = Action.NO_ACTION) }
        LaunchedEffect(key1 = action) {
            if (action != myAction) {
                myAction = action
                sharedViewModel.updateAction(
                    newAction = action
                )
            }
        }

        val databaseAction = sharedViewModel.action
        ListScreen(
            action = databaseAction,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel,
        )
    }
}