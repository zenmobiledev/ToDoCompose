package com.mobbelldev.todocompose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mobbelldev.todocompose.data.model.Priority
import com.mobbelldev.todocompose.data.model.ToDoTask
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel
import com.mobbelldev.todocompose.util.Action


@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit,
) {
    val title: String = sharedViewModel.title
    val description: String = sharedViewModel.description
    val priority: Priority = sharedViewModel.priority

    val context: Context = LocalContext.current

    BackHandler(onBackPressed = { navigateToListScreen(Action.NO_ACTION) })

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(
                                context = context
                            )
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                TaskContent(
                    title = title,
                    onTitleChange = {
                        sharedViewModel.updateTitle(newTitle = it)
                    },
                    description = description,
                    onDescriptionChange = {
                        sharedViewModel.updateDescription(
                            newDescription = it
                        )
                    },
                    priority = priority,
                    onPrioritySelected = {
                        sharedViewModel.updatePriority(
                            newPriority = it
                        )
                    }
                )
            }
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(context, "Fields Empty.", Toast.LENGTH_SHORT).show()
}

@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit,
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled = true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(backDispatcher) {
        backDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}
