package com.example.apiexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apiexplorer.screens.RequestScreen
import com.example.apiexplorer.screens.ResponseScreen

// Rutele de navigare din aplicație.
// Aceste constante ne ajută să evităm greșelile de scriere.
object Routes {
    const val REQUEST = "request"   // Ecranul unde se introduce cererea API.
    const val RESPONSE = "response" // Ecranul unde se afișează răspunsul.
}

@Composable
fun AppNavHost(navController: NavHostController) {
    // Stabilim un host de navigare cu destinația de start setată la REQUEST.
    NavHost(navController = navController, startDestination = Routes.REQUEST) {
        // Când ruta e "request", se afișează ecranul pentru cereri API.
        composable(Routes.REQUEST) {
            RequestScreen(navController = navController)
        }
        // Când ruta e "response", se afișează ecranul pentru răspunsul API.
        composable(Routes.RESPONSE) {
            ResponseScreen(navController = navController)
        }
    }
}
