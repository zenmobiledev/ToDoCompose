package com.mobbelldev.todocompose.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.mobbelldev.todocompose.R
import com.mobbelldev.todocompose.components.PriorityItem
import com.mobbelldev.todocompose.data.model.Priority
import com.mobbelldev.todocompose.ui.theme.LARGE_PADDING
import com.mobbelldev.todocompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.mobbelldev.todocompose.ui.theme.TOP_BAR_ELEVATION

@Composable
fun ListAppBar() {
//    DefaultListAppBar(
//        onSearchClicked = {
//
//        },
//        onSortClicked = {
//
//        },
//
//        onDeleteAction = {
//
//        },
//    )

    SearchAppBar(
        text = "",
        onTextChange = {},
        onClosedClicked = {},
        onSearchClicked = {}
    )
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
            Text(text = "Tasks")
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAction = onDeleteAction,
            )
        }
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
            onSearchClicked
            println("Search icon is clicked!")
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_action),
        )
    }
}

@Composable
fun SortAction(onSortClicked: (Priority) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(R.drawable.ic_filter_list_24),
            contentDescription = stringResource(R.string.sort_action),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                },
                text = {
                    PriorityItem(Priority.LOW)
                },
            )
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                },
                text = {
                    PriorityItem(Priority.HIGH)
                },
            )
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                },
                text = {
                    PriorityItem(Priority.NONE)
                },
            )
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteAction: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(R.drawable.ic_more_vert_24),
            contentDescription = stringResource(R.string.delete_all_action),
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
                    modifier = Modifier.alpha(0.5F),
                    text = "Search"
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(alpha = 0.5F),
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { onClosedClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
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