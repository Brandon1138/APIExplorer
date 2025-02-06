package com.example.apiexplorer.data

import com.example.apiexplorer.screens.ApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object ApiService {
    suspend fun makeRequest(apiRequest: ApiRequest): String = withContext(Dispatchers.IO) {
        try {
            val connection = URL(apiRequest.url).openConnection() as HttpURLConnection
            connection.requestMethod = apiRequest.method
            // Optionally, parse and set headers from apiRequest.headers
            if (apiRequest.method.uppercase() != "GET") {
                connection.doOutput = true
                connection.outputStream.use { os ->
                    os.write(apiRequest.body.toByteArray())
                }
            }
            val responseCode = connection.responseCode
            val responseMessage = connection.responseMessage
            val responseBody = connection.inputStream.bufferedReader().readText()
            "Response Code: $responseCode\nResponse Message: $responseMessage\n\n$responseBody"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
