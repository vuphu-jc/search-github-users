package com.example.searchgithubusers.ui.activity.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchgithubusers.R
import com.example.searchgithubusers.application.MainApplication
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.ui.activity.main.MainContract
import com.example.searchgithubusers.ui.base.BaseActivity
import com.example.searchgithubusers.ui.recyclerview.GithubUserRecyclerViewAdapter
import com.example.searchgithubusers.ui.recyclerview.base.CustomRecyclerView
import com.example.searchgithubusers.utils.ActivityUtils
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject


class SearchActivity : BaseActivity(), SearchContract.View {

    @Inject
    lateinit var mPresenter: SearchContract.Presenter

    private val mItems = mutableListOf<CustomRecyclerView.Item>()
    private var mUsersAdapter = GithubUserRecyclerViewAdapter(this, mItems)
    private var mLoadTimes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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
        mUsersAdapter.showLoadingProgress(isShow)
    }

    override fun loadDataSuccess(data: List<GithubUser>) {
        val oldPosition = mItems.size
        mItems.addAll(data)
        if (data.size > 0) {
            mUsersAdapter.notifyItemRangeInserted(oldPosition, data.size)
            mLoadTimes++
        }
    }

    override fun loadDataFailed(message: String) {
        mUsersAdapter.showError(message)
    }

    private fun injectDependency() {
        DaggerSearchComponent.builder()
            .appComponent((application as MainApplication).getAppComponent())
            .searchModule(SearchModule())
            .build()
            .inject(this)
    }


    private fun initialize() {
        usersRecyclerView.adapter = mUsersAdapter
        usersRecyclerView.layoutManager = LinearLayoutManager(this)

        searchEditText.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mUsersAdapter.clearAllData()
                    mLoadTimes = 1
                    mPresenter.loadData(searchEditText.text.toString(), mLoadTimes)
                    ActivityUtils.hideKeyboard(this@SearchActivity)
                    return true;
                }
                return false;
            }
        })

        usersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                if (!mUsersAdapter.isLoading() && linearLayoutManager != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == mItems.size - 1) {
                    mPresenter.loadData(searchEditText.text.toString(), mLoadTimes)
                }
            }
        })
    }
}
