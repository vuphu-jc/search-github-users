package com.example.searchgithubusers.model.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){

    companion object {
        private const val BASE_API_URL = "https://api.github.com/"

        @Volatile
        private var sInstance: RetrofitClient? = null

        fun getInstance(): RetrofitClient {
            val tempInstance = sInstance
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = RetrofitClient()
                sInstance = instance
                return instance
            }
        }
    }

    private var mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder().baseUrl(BASE_API_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
    }

    fun getAPIService(): RetrofitAPIService {
        return mRetrofit.create(RetrofitAPIService::class.java)
    }
}