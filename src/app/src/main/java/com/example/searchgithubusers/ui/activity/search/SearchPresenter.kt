package com.example.searchgithubusers.ui.activity.search

import android.os.Handler
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.model.repository.GithubUserRepositoryImp
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchPresenter: SearchContract.Presenter<SearchContract.View> {

    companion object {
        private const val PER_PAGE = 20
    }

    private var mView: SearchContract.View? = null
    private var mRepository = GithubUserRepositoryImp()

    override fun detach() {
        mView = null
    }

    override fun attach(view: SearchContract.View) {
        mView = view
    }

    override fun loadData(name: String, times: Int) {
        mView?.showProgressLoadData(true)
        mRepository.fetchUsersByName(name, PER_PAGE, times)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<GithubUser>> {
                override fun onError(e: Throwable?) {
                    mView?.showProgressLoadData(false)
                }

                override fun onNext(t: List<GithubUser>?) {
                    mView?.showProgressLoadData(false)
                    if (t != null)
                        mView?.loadDataSuccess(t)
                }

                override fun onCompleted() {
                }
            })
    }
}