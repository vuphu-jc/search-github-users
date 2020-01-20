package com.example.searchgithubusers.extension

import android.content.Context

fun Context.getColor(resId: Int): Int {
    return this.resources.getColor(resId)
}