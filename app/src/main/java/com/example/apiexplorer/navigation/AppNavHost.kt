package com.example.apiexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apiexplorer.screens.RequestScreen
import com.example.apiexplorer.screens.ResponseScreen

object Routes {
    const val REQUEST = "request"
    const val RESPONSE = "response"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.REQUEST) {
        composable(Routes.REQUEST) {
            RequestScreen(navController = navController)
        }
        composable(Routes.RESPONSE) {
            ResponseScreen(navController = navController)
        }
    }
}
