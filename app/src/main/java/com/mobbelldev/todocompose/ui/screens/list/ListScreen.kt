package com.mobbelldev.todocompose.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mobbelldev.todocompose.R
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel
import com.mobbelldev.todocompose.util.SearchBarAppState

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchBarAppState: SearchBarAppState by sharedViewModel.searchBarAppState
    val searchTextState: String by sharedViewModel.searchTextState

    Scaffold(
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchBarAppState = searchBarAppState,
                searchTextState = searchTextState
            )
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                ListContent(
                    tasks = allTasks,
                    navigateToTaskScreen = navigateToTaskScreen
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
            tint = MaterialTheme.colorScheme.primary
        )
    }
}