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
import com.example.apiexplorer.data.ApiService // Import necesar pentru a face cereri API

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponseScreen(navController: NavHostController) {
    // Obținem cererea API din ecranul anterior, dacă există.
    val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
    val apiRequest = savedStateHandle?.get<ApiRequest>("api_request")

    // Mesajul inițial afișat până primim răspunsul.
    var responseText by remember { mutableStateOf("Awaiting response...") }

    // Efectuăm cererea API în mod asincron. Blocul se reexecută dacă apiRequest se schimbă.
    LaunchedEffect(apiRequest) {
        apiRequest?.let { request ->
            launch {
                responseText = try {
                    // Apelăm serviciul API și actualizăm textul cu răspunsul.
                    ApiService.makeRequest(request)
                } catch (e: Exception) {
                    // În caz de eroare, afișăm mesajul corespunzător.
                    "Error: ${e.message}"
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API Response") },
                actions = {
                    // Butonul de navigare înapoi.
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                    // Butonul de exit, care revine la ecranul anterior.
                    IconButton(onClick = { navController.popBackStack() }) {
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
                // Afișăm textul cu rezultatul cererii API.
                Text(text = responseText)
            }
        }
    )
}
