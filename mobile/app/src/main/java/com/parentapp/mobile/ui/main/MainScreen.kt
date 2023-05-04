package com.parentapp.mobile.ui.main

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.parentapp.mobile.ui.login.Login

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parent Assist App") }
            )
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("about") {
                AboutScreen()
            }
            composable("login") {
                Login()
            }
        }
    }
}

