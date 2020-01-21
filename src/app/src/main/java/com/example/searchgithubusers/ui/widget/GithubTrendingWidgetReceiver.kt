package com.example.searchgithubusers.ui.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri

class GithubTrendingWidgetReceiver: BroadcastReceiver() {
    companion object {
        private const val ACTION = "com.example.searchgithubusers.NAVIGATE_TO_REPOSITORY"
        private const val URL = "URL"

        fun navigateToRepositoryIntent(context: Context, url: String): Intent {
            val intent = Intent(context, GithubTrendingWidgetReceiver::class.java)
            intent.action = ACTION
            intent.putExtra(URL, url)
            return intent
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null && intent.action == ACTION) {
            val url = intent.getStringExtra(URL)
            if (url != null) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                i.addFlags(FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)
            }
        }
    }

}