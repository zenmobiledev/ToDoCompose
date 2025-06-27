package com.mobbelldev.todocompose.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.mobbelldev.todocompose.R
import com.mobbelldev.todocompose.components.PriorityItem
import com.mobbelldev.todocompose.data.model.Priority
import com.mobbelldev.todocompose.ui.theme.LARGE_PADDING
import com.mobbelldev.todocompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.mobbelldev.todocompose.ui.theme.TOP_BAR_ELEVATION
import com.mobbelldev.todocompose.ui.viewmodel.SharedViewModel
import com.mobbelldev.todocompose.util.SearchBarAppState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchBarAppState: SearchBarAppState,
    searchTextState: String,
) {

    when (searchBarAppState) {
        SearchBarAppState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.updateAppBarState(
                        newState = SearchBarAppState.OPENED
                    )
                },
                onSortClicked = {

                },
                onDeleteAction = {

                },
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText ->
                    sharedViewModel.updateSearchText(
                        newText = newText
                    )
                },
                onClosedClicked = {
                    sharedViewModel.updateAppBarState(
                        newState = SearchBarAppState.CLOSED
                    )
                    sharedViewModel.updateSearchText(
                        newText = ""
                    )
                },
                onSearchClicked = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAction: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = stringResource(R.string.list_screen_title)
            )
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAction = onDeleteAction,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAction: () -> Unit,
) {
    SearchAction(
        onSearchClicked = onSearchClicked
    )

    SortAction(
        onSortClicked = onSortClicked
    )

    DeleteAllAction(
        onDeleteAction = onDeleteAction
    )
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit,
) {
    IconButton(
        onClick = {
            onSearchClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_action),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit,
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_filter_list_24),
            contentDescription = stringResource(R.string.sort_action),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            Priority.entries.toTypedArray().slice(setOf(0, 2, 3)).forEach { priority ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSortClicked(priority)
                    },
                    text = {
                        PriorityItem(
                            priority = priority
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteAction: () -> Unit,
) {
    var expanded: Boolean by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_more_vert_24),
            contentDescription = stringResource(R.string.delete_all_action),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.padding(start = LARGE_PADDING),
                        text = stringResource(R.string.delete_all_action),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteAction()
                },
            )
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onClosedClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        shadowElevation = TOP_BAR_ELEVATION,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(0.5F),
                    text = stringResource(R.string.search_placeholder),
                    color = Color.Black
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(alpha = 0.38F),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onClosedClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon),
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                },
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {

        },
        onSortClicked = {

        },
        onDeleteAction = {

        },
    )
}

@Preview
@Composable
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onClosedClicked = {},
        onSearchClicked = {}
    )
}