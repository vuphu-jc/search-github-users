package com.example.searchgithubusers.ui.activity.main

import android.content.Intent
import android.os.Bundle
import com.example.searchgithubusers.Global
import com.example.searchgithubusers.R
import com.example.searchgithubusers.ui.base.BaseActivity
import com.example.searchgithubusers.ui.activity.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Global.initialize(applicationContext)
        initialize()
    }

    private fun initialize() {
        searchLinearLayout.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}
