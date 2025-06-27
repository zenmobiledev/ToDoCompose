package com.mobbelldev.todocompose.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.mobbelldev.todocompose.data.model.Priority
import com.mobbelldev.todocompose.data.model.ToDoTask
import com.mobbelldev.todocompose.ui.theme.LARGE_PADDING
import com.mobbelldev.todocompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.mobbelldev.todocompose.ui.theme.TASK_ITEM_ELEVATION
import com.mobbelldev.todocompose.util.RequestState

@Composable
fun ListContent(
    tasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    if (tasks is RequestState.Success) {
        if (tasks.data.isEmpty()) {
            EmptyContent()
        } else {
            DisplayTasks(
                tasks = tasks.data,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@Composable
fun DisplayTasks(
    modifier: Modifier = Modifier,
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = tasks,
            key = { task ->
                task.id
            },
        ) { task ->
            TaskItem(
                toDoTask = task, navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8F),
                    text = toDoTask.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "Title",
            description = "Lorem ipsum silit dolor amet",
            priority = Priority.MEDIUM
        ),
        navigateToTaskScreen = {

        }
    )

}