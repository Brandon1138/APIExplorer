package com.example.apiexplorer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp // Folosim autoMirrored pentru suport RTL
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.apiexplorer.navigation.Routes
import java.io.Serializable

// Modelul datelor pentru cererea API; îl salvăm ca Serializable pentru a-l putea transmite între ecrane.
data class ApiRequest(
    val url: String,
    val method: String,
    val headers: String,
    val body: String
) : Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestScreen(navController: NavHostController) {
    // Stările locale pentru câmpurile de input.
    var url by remember { mutableStateOf("") }
    var method by remember { mutableStateOf("GET") }
    var headers by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API Request") },
                actions = {
                    // Butonul de exit: apasă pentru a reveni la ecranul precedent.
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Exit"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                // Input pentru URL-ul API
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("URL") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Input pentru metoda HTTP, de ex. GET sau POST
                OutlinedTextField(
                    value = method,
                    onValueChange = { method = it },
                    label = { Text("Method") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Input pentru anteturi, introduse în format JSON
                OutlinedTextField(
                    value = headers,
                    onValueChange = { headers = it },
                    label = { Text("Headers (JSON)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Input pentru corpul cererii
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text("Body") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Buton pentru trimiterea cererii; salvăm datele și navigăm către ecranul de răspuns.
                Button(
                    onClick = {
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("api_request", ApiRequest(url, method, headers, body))
                        navController.navigate(Routes.RESPONSE)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send Request")
                }
            }
        }
    )
}
