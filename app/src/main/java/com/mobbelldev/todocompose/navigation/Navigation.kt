package com.mobbelldev.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mobbelldev.todocompose.navigation.destinations.listComposable
import com.mobbelldev.todocompose.navigation.destinations.taskComposable
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel

@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel,
) {

    NavHost(
        navController = navHostController,
        startDestination = Screens.List()
    ) {
        listComposable(
            navigateToTaskScreen = { taskId ->
                navHostController.navigate(Screens.Task(id = taskId))
            },
            sharedViewModel = sharedViewModel,
        )

        taskComposable(
            navigateToTaskScreen = { action ->
                navHostController.navigate(Screens.List(action = action)) {
                    popUpTo(Screens.Task) { inclusive = true }
                }
            },
            sharedViewModel = sharedViewModel,
        )
    }
}