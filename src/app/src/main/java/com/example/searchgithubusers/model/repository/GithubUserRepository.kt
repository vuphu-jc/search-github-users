package com.example.searchgithubusers.model.repository

import android.util.Log
import com.example.searchgithubusers.model.api.RetrofitClient
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.model.dto.ListGithubUser
import com.example.searchgithubusers.utils.HttpURLConnectionUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import retrofit2.Retrofit
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func0
import rx.schedulers.Schedulers
import java.util.concurrent.Callable

interface GithubUserRepository {
    fun fetchUsersByName(name: String, perPage: Int, page: Int): Observable<List<GithubUser>>
    fun fetchAllUsersByName(name: String): Observable<List<GithubUser>>
}

class GithubUserRepositoryImp: GithubUserRepository {

    companion object {
        private const val API_SOURCE = "https://api.github.com/search"
    }

    override fun fetchUsersByName(name: String, perPage: Int, page: Int): Observable<List<GithubUser>> {
        return Observable.defer(object: Func0<Observable<List<GithubUser>>> {
            override fun call(): Observable<List<GithubUser>> {
                val url = "$API_SOURCE/users?q=$name&per_page=$perPage&page=$page"
                val jsonContent = HttpURLConnectionUtils.getContent(url)
                val data = (Gson()).fromJson(jsonContent, ListGithubUser::class.java).items
                return Observable.just(data)
            }
        })
    }

    override fun fetchAllUsersByName(name: String): Observable<List<GithubUser>> {
        return Observable.defer(object: Func0<Observable<List<GithubUser>>> {
            override fun call(): Observable<List<GithubUser>> {
                val url = "$API_SOURCE/users?q=$name"
                val jsonContent = HttpURLConnectionUtils.getContent(url)
                val data = (Gson()).fromJson(jsonContent, ListGithubUser::class.java).items
                return Observable.just(data)
            }
        })
    }
}

class GithubUserRepositoryRetrofitImp: GithubUserRepository {
    override fun fetchUsersByName(
        name: String,
        perPage: Int,
        page: Int
    ): Observable<List<GithubUser>> {
        return RetrofitClient.getInstance().getAPIService().fetchUsersByName(name, perPage, page)
            .map { it.items }
    }

    override fun fetchAllUsersByName(name: String): Observable<List<GithubUser>> {
        return RetrofitClient.getInstance().getAPIService().fetchAllUsersByName(name)
            .map { it.items }
    }
}