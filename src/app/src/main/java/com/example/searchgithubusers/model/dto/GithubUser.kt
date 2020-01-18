package com.example.searchgithubusers.model.dto

import android.os.Parcelable
import com.example.searchgithubusers.ui.recyclerview.base.CustomRecyclerView
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUser
    (@SerializedName("login") val login: String,
     @SerializedName("id") val id: Int,
     @SerializedName("avatar_url") val avatarURL: String,
     @SerializedName("html_url") val htmlURL: String,
     @SerializedName("repos_url") val reposURL: String,
     @SerializedName("score") val score: Float)
    : Parcelable, CustomRecyclerView.Item {
}

@Parcelize
data class ListGithubUser
    (@SerializedName("items") val items: List<GithubUser>) : Parcelable {
}