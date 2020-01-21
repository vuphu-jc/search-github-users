package com.example.searchgithubusers.model.repository

import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.model.dto.GithubTrending
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.model.dto.ListGithubUser
import com.example.searchgithubusers.utils.HttpURLConnectionUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rx.Observable
import rx.Subscriber
import rx.functions.Func0

interface GithubTrendingRepository {
    fun fetchTrending(): Observable<List<GithubTrending>>
}

class GithubTrendingRepositoryImp: GithubTrendingRepository {
    companion object {
        private const val API_SOURCE = "https://github-trending-api.now.sh/repositories?since=daily"
    }

    fun toGithubTrendingArray(jsonContent: String): List<GithubTrending> {
        val itemType = object : TypeToken<List<GithubTrending>>() {}.type
        val data = (Gson()).fromJson<List<GithubTrending>>(jsonContent, itemType)
        return data
    }

    override fun fetchTrending(): Observable<List<GithubTrending>> {
        return Observable.defer {
            val jsonContent = HttpURLConnectionUtils.getContent(API_SOURCE)
            Observable.just(toGithubTrendingArray(jsonContent))
        }
    }
}