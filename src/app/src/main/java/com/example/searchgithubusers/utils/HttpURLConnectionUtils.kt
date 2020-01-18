package com.example.searchgithubusers.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


object HttpURLConnectionUtils {
    fun getContent(url: String): String {
        var urlConnection: HttpURLConnection? = null
        val url = URL(url)

        urlConnection = url.openConnection() as HttpURLConnection
        val data = urlConnection.inputStream.bufferedReader().use { it.readText() }
        return data
    }
}