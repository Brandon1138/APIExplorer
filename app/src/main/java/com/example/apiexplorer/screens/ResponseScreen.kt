package com.example.apiexplorer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.example.apiexplorer.data.ApiService // ADDED IMPORT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponseScreen(navController: NavHostController) {
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val apiRequest = savedStateHandle?.get<ApiRequest>("api_request")
    var responseText by remember { mutableStateOf("Awaiting response...") }

    LaunchedEffect(apiRequest) {
        apiRequest?.let { request ->
            // Simulate network request (MUST be in a coroutine)
            launch { // Use launch for a suspending function call
                responseText = try {
                    ApiService.makeRequest(request)
                } catch (e: Exception) {
                    "Error: ${e.message}" // Handle errors gracefully
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API Response") },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    IconButton(onClick = {
                        navController.popBackStack() // Add exit logic here, likely popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Exit")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(text = responseText)
            }
        }
    )
}