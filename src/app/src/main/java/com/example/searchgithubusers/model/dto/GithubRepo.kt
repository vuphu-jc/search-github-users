package com.example.searchgithubusers.model.dto

import android.os.Parcelable
import com.example.searchgithubusers.ui.recyclerview.base.CustomRecyclerView
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: GithubUser,
    @SerializedName("html_url") val htmlURL: String)
    : Parcelable, CustomRecyclerView.Item {
}