package com.example.apiexplorer.data

import com.example.apiexplorer.screens.ApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

// Serviciul care se ocupă de trimiterea cererilor HTTP.
// Acest obiect este folosit pentru a comunica cu serverele pe baza datelor primite.
object ApiService {
    // Funcție suspendată ce efectuează o cerere HTTP în mod asincron pe un thread dedicat (IO).
    suspend fun makeRequest(apiRequest: ApiRequest): String = withContext(Dispatchers.IO) {
        try {
            // Deschidem conexiunea către URL-ul specificat în cererea API.
            val connection = URL(apiRequest.url).openConnection() as HttpURLConnection
            connection.requestMethod = apiRequest.method

            // Dacă metoda nu este GET, pregătim trimiterea datelor în corpul cererii.
            if (apiRequest.method.uppercase() != "GET") {
                connection.doOutput = true
                connection.outputStream.use { os ->
                    // Convertim corpul cererii în bytes și îl trimitem.
                    os.write(apiRequest.body.toByteArray())
                }
            }

            // Extragem codul de răspuns și mesajul de la server.
            val responseCode = connection.responseCode
            val responseMessage = connection.responseMessage
            // Citim întregul conținut al răspunsului.
            val responseBody = connection.inputStream.bufferedReader().readText()

            // Formatează rezultatul pentru a afișa toate informațiile importante.
            "Response Code: $responseCode\nResponse Message: $responseMessage\n\n$responseBody"
        } catch (e: Exception) {
            // În caz de eroare, returnăm mesajul de eroare primit.
            "Error: ${e.message}"
        }
    }
}
