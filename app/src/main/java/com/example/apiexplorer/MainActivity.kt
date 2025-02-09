package com.example.apiexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.apiexplorer.navigation.AppNavHost
import com.example.apiexplorer.ui.theme.APIExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permit aplicației să se extindă pe întregul ecran, fără margini inutile.
        enableEdgeToEdge()
        setContent {
            APIExplorerTheme {
                // Creăm controller-ul de navigație pentru a gestiona tranzițiile între ecrane.
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        // Pornim fluxul principal de navigare al aplicației.
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    APIExplorerTheme {
        // Un preview simplu, util pentru verificarea aspectului temei.
        Box(modifier = Modifier.fillMaxSize())
    }
}
