package com.example.eduspark.domain

import android.util.Log
import com.example.eduspark.local.support
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class HttpHandler {
    fun getRequest( endpoint: String): String {
        var response = ""
        try {
            var url = URL(support.url + endpoint)
            var conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            response = conn.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.d("HttpHandler", "Eror ${e.message}")
        } catch (e: IOException) {
            Log.d("HttpHandler", "Eror ${e.message}")
        }

        return response
    }

    fun postRequest(endpoint: String, requestBody: String) : String {
        var response = ""
        try {
            var url = URL(support.url + endpoint)
            var conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.outputStream.use { it.write(requestBody.toByteArray()) }

            response = conn.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.d("HttpHandler", "Eror : ${e.message}")
        }

        return response
    }



}