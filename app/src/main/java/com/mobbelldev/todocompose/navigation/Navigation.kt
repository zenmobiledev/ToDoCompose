package com.mobbelldev.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mobbelldev.todocompose.navigation.destinations.listComposable
import com.mobbelldev.todocompose.navigation.destinations.taskComposable
import com.mobbelldev.todocompose.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(navHostController: NavHostController) {
    val screen = remember(key1 = navHostController) {
        Screens(
            navHostController = navHostController
        )
    }

    NavHost(
        navController = navHostController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task
        )

        taskComposable(
            navigateToTaskScreen = screen.list
        )
    }
}