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
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.ui.base.BaseActivity
import com.example.searchgithubusers.ui.recyclerview.GithubUserRecyclerViewAdapter
import com.example.searchgithubusers.ui.recyclerview.base.CustomRecyclerView
import com.example.searchgithubusers.utils.ActivityUtils
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity(), SearchContract.View {

    private val mItems = mutableListOf<CustomRecyclerView.Item>()
    private var mUsersAdapter = GithubUserRecyclerViewAdapter(this, mItems)
    private val mPresenter = SearchPresenter()
    private var mLoadTimes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initialize()
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

    private fun initialize() {
        usersRecyclerView.adapter = mUsersAdapter
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        mPresenter.attach(this)

        searchEditText.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val oldSize = mItems.size
                    mItems.clear()
                    mUsersAdapter.notifyItemRangeRemoved(0, oldSize)
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
