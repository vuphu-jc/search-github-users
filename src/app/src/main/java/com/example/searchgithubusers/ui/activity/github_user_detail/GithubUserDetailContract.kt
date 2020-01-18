package com.example.searchgithubusers.ui.activity.github_user_detail

import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.ui.base.BaseContract

interface GithubUserDetailContract {
    interface View: BaseContract.View {
        fun showProgressLoadData(isShow: Boolean)
        fun loadDataSuccess(data: List<GithubRepo>)
    }
    interface Presenter: BaseContract.Presenter<View> {
        fun loadRepo(name: String)
    }
}