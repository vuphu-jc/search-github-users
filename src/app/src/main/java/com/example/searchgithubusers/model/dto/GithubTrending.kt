package com.example.searchgithubusers.model.dto

import android.os.Parcelable
import com.example.searchgithubusers.ui.recyclerview.base.CustomRecyclerView
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubTrending(
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("url") val url: String,
    @SerializedName("stars") val stars: Int)
    : Parcelable, CustomRecyclerView.Item {
}