package com.example.searchgithubusers.model.repository

import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.utils.HttpURLConnectionUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rx.Observable
import rx.Subscriber
import rx.functions.Func0
import java.lang.reflect.Type


interface GithubRepoRepository {
    fun fetchAllRepo(name: String): Observable<List<GithubRepo>>
}

class GithubRepoRepositoryImp: GithubRepoRepository {
    companion object {
        private const val API_SOURCE = "https://api.github.com"
    }

    override fun fetchAllRepo(name: String): Observable<List<GithubRepo>> {
        return Observable.create(object: Observable.OnSubscribe<List<GithubRepo>> {
            override fun call(t: Subscriber<in List<GithubRepo>>?) {
                val url = "$API_SOURCE/users/$name/repos"
                val jsonContent = HttpURLConnectionUtils.getContent(url)
                val itemType = object : TypeToken<List<GithubRepo>>() {}.type
                val data = (Gson()).fromJson<List<GithubRepo>>(jsonContent, itemType)
                t?.onNext(data)
                t?.onCompleted()
            }
        })
    }
}