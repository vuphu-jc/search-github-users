package com.example.searchgithubusers.ui.activity.search

import android.os.Handler
import com.example.searchgithubusers.application.MainApplication
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.model.repository.GithubUserRepository
import com.example.searchgithubusers.model.repository.GithubUserRepositoryImp
import com.example.searchgithubusers.model.repository.GithubUserRepositoryRetrofitImp
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter: SearchContract.Presenter {

    companion object {
        private const val PER_PAGE = 20
    }

    @Inject
    lateinit var mRepository: GithubUserRepository
    private var mView: SearchContract.View? = null

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
                    mView?.loadDataFailed(e?.message.toString())
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