package com.example.searchgithubusers.ui.activity.search

import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.ui.base.BaseContract

interface SearchContract {
    interface View: BaseContract.View {
        fun showProgressLoadData(isShow: Boolean)
        fun loadDataSuccess(data: List<GithubUser>)
    }
    interface Presenter<T> : BaseContract.Presenter<View> {
        fun loadData(name: String, times: Int = 1)
    }
}