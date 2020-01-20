package com.example.searchgithubusers.ui.activity.github_user_detail

import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.model.repository.GithubRepoRepository
import com.example.searchgithubusers.model.repository.GithubRepoRepositoryImp
import com.example.searchgithubusers.model.repository.GithubUserRepositoryImp
import com.example.searchgithubusers.ui.activity.search.SearchContract
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class GithubUserDetailPresenter: GithubUserDetailContract.Presenter {

    private var mView: GithubUserDetailContract.View? = null
    private var mRepository = GithubRepoRepositoryImp()

    override fun detach() {
        mView = null
    }

    override fun attach(view: GithubUserDetailContract.View) {
        mView = view
    }

    override fun loadRepo(name: String) {
        mView?.showProgressLoadData(true)
        mRepository.fetchAllRepo(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<GithubRepo>> {
                override fun onError(e: Throwable?) {
                    mView?.showProgressLoadData(false)
                }

                override fun onNext(t: List<GithubRepo>?) {
                    mView?.showProgressLoadData(false)
                    if (t != null)
                        mView?.loadDataSuccess(t)
                }

                override fun onCompleted() {
                }
            })
    }
}