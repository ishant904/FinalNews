package com.example.finalnews.Utilities

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class GetOkHttpResponse (private val client: OkHttpClient, private val request: Request){

    fun run(): String? {
        // Request request = new Request.Builder().url(url).build();
        try {
            val response = client.newCall(request).execute()
            return response.body()?.string()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}