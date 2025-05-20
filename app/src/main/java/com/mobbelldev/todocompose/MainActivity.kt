package com.mobbelldev.todocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mobbelldev.todocompose.navigation.SetupNavigation
import com.mobbelldev.todocompose.ui.theme.ToDoComposeTheme

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoComposeTheme {
                navHostController = rememberNavController()
                SetupNavigation(
                    navHostController = navHostController
                )
            }
        }
    }
}