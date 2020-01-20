package com.example.searchgithubusers.model.api

import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.model.dto.ListGithubUser
import retrofit2.Call
import retrofit2.http.*
import rx.Observable
import java.util.*

interface RetrofitAPIService {
    @GET("search/users?")
    fun fetchUsersByName(@Query("q") name: String,
                         @Query("per_page") perPage: Int,
                         @Query("page") page: Int): Observable<ListGithubUser>

    @GET("search/users?")
    fun fetchAllUsersByName(@Query("q") name: String): Observable<ListGithubUser>

    @GET("users/{user_login}/repos")
    fun getRepos(@Path("user_login") name: String): Observable<List<GithubRepo>>
}