package com.mobbelldev.todocompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mobbelldev.todocompose.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
) {
    Scaffold(
        content = {},
        floatingActionButton = {
            ListFAB(
                onFabClicked = navigateToTaskScreen
            )
        },
    )
}

@Composable
fun ListFAB(
    onFabClicked: (Int) -> Unit,
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
        )
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    ListScreen(
        navigateToTaskScreen = {}
    )
}