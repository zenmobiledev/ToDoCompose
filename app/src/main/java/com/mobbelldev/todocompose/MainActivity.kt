package com.mobbelldev.todocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobbelldev.todocompose.ui.screens.ListAppBar
import com.mobbelldev.todocompose.ui.screens.ListScreen
import com.mobbelldev.todocompose.ui.theme.ToDoComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoComposeTheme {
                ListScreen {}
            }
        }
    }
}

@Preview
@Composable
private fun MainActivityPrev() {
    ToDoComposeTheme {
        ListScreen {

        }
    }
}