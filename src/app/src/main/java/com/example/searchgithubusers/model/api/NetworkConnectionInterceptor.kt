package com.example.searchgithubusers.model.api

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

// From 'https://medium.com/programming-lite/retrofit-2-handling-network-error-defc7d373ad1'
class NetworkConnectionInterceptor(private val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException()
        }

        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    fun isConnected(): Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}