package com.mobbelldev.todocompose.navigation

import com.mobbelldev.todocompose.util.Action
import kotlinx.serialization.Serializable

//class Screens(navHostController: NavHostController) {
//    val list: (Action) -> Unit = { action ->
//        navHostController.navigate("list/${action.name}") {
//            popUpTo(LIST_SCREEN) {
//                inclusive = true
//            }
//        }
//    }
//
//    val task: (Int) -> Unit = { taskId ->
//        navHostController.navigate("task/$taskId")
//    }
//}

@Serializable
sealed class Screen {
    @Serializable
    data class List(val action: Action = Action.NO_ACTION) : Screen()

    @Serializable
    data class Task(val id: Int) : Screen()
}