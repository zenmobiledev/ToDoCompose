package com.mobbelldev.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mobbelldev.todocompose.ui.screens.ListScreen
import com.mobbelldev.todocompose.util.Constants.LIST_ARGUMENT_KEY
import com.mobbelldev.todocompose.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType

            nullable = true
        })
    ) {
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}