package com.mobbelldev.todocompose.navigation.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mobbelldev.todocompose.navigation.Screens
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel
import com.mobbelldev.todocompose.util.Action

fun NavGraphBuilder.taskComposable(
    navigateToTaskScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    composable<Screens.Task>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 3000
                )
            )
        }
    ) {

    }
}