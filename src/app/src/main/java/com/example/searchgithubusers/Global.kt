package com.example.searchgithubusers

import android.content.Context

object Global {
    private lateinit var mContext: Context
    fun initialize(context: Context) {
        this.mContext = context
    }

    fun getContext(): Context {
        return mContext
    }
}