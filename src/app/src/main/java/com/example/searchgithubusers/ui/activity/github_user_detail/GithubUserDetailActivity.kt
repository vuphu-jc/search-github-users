package com.example.searchgithubusers.ui.activity.github_user_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.searchgithubusers.R
import com.example.searchgithubusers.application.MainApplication
import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.ui.activity.search.DaggerSearchComponent
import com.example.searchgithubusers.ui.activity.search.SearchModule
import com.example.searchgithubusers.ui.activity.search.SearchPresenter
import com.example.searchgithubusers.ui.base.BaseActivity
import com.example.searchgithubusers.ui.recyclerview.GithubRepoRecyclerViewAdapter
import com.example.searchgithubusers.ui.recyclerview.GithubUserRecyclerViewAdapter
import com.example.searchgithubusers.ui.recyclerview.base.CustomRecyclerView
import kotlinx.android.synthetic.main.activity_github_user_detail.*
import javax.inject.Inject

class GithubUserDetailActivity : BaseActivity(), GithubUserDetailContract.View {

    companion object {
        const val GITHUB_USER_EXTRA = "GITHUB_USER"
    }

    @Inject
    lateinit var mPresenter: GithubUserDetailContract.Presenter

    private var mGithubUserData: GithubUser? = null
    private val mItems = mutableListOf<CustomRecyclerView.Item>()
    private var mReposAdapter = GithubRepoRecyclerViewAdapter(this, mItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)

        mGithubUserData = intent.getParcelableExtra(GITHUB_USER_EXTRA)
        injectDependency()
        initialize()
    }

    override fun onStart() {
        mPresenter.attach(this)
        super.onStart()
    }

    override fun onDestroy() {
        mPresenter.detach()
        super.onDestroy()
    }

    override fun showProgressLoadData(isShow: Boolean) {
        mReposAdapter.showLoadingProgress(isShow)
    }

    override fun loadDataSuccess(data: List<GithubRepo>) {
        mItems.addAll(data)
        mReposAdapter.notifyItemRangeInserted(0, data.size)
    }

    override fun loadDataFailed(message: String) {
        mReposAdapter.showError(message)
    }

    private fun injectDependency() {
        DaggerGithubUserDetailComponent.builder()
            .appComponent((application as MainApplication).getAppComponent())
            .githubUserDetailModule(GithubUserDetailModule())
            .build()
            .inject(this)
    }

    private fun initialize() {
        if (mGithubUserData == null)
            finish()
        Glide.with(this).load(mGithubUserData?.avatarURL).into(avatarImageView)
        loginTextView.text = mGithubUserData?.login
        idTextView.text = mGithubUserData?.id.toString()

        reposRecyclerView.adapter = mReposAdapter
        reposRecyclerView.layoutManager = LinearLayoutManager(this)
        mPresenter.attach(this)

        val login: String? = mGithubUserData?.login
        if (login != null)
            mPresenter.loadRepo(login)
    }
}
