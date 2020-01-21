package com.example.searchgithubusers.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.NotificationTarget
import com.bumptech.glide.request.transition.Transition
import com.example.searchgithubusers.R
import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.model.dto.GithubTrending
import com.example.searchgithubusers.model.repository.GithubTrendingRepository
import com.example.searchgithubusers.model.repository.GithubTrendingRepositoryImp
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class GithubTrendingWidgetProvider: AppWidgetProvider() {
    companion object {
        private var MAX_TRENDING_NUMBER = 3

        fun createWidgetView(context: Context, trendings: List<GithubTrending>, vararg appWidgetIds: Int): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.widget_github_trending)
            val nTrendings = Math.min(MAX_TRENDING_NUMBER, trendings.size)
            for (i in 0 until nTrendings) {
                val subViews = RemoteViews(context.packageName, R.layout.widget_item_github_trending)
                val trending = trendings[i]
                subViews.setTextViewText(R.id.loginTextView, trending.name)
                subViews.setTextViewText(R.id.starsTextView, trending.stars.toString())
                val target = AppWidgetTarget(context, R.id.avatarImageView, subViews, *appWidgetIds)
                Glide.with(context).asBitmap().load(trending.url).into(target)
                views.addView(R.id.layoutLinearLayout, subViews)

                val intent = GithubTrendingWidgetReceiver.navigateToRepositoryIntent(context, trending.url)
                val pIntent = PendingIntent.getBroadcast(context, i, intent, 0)
                subViews.setOnClickPendingIntent(R.id.trendingLinearLayout, pIntent)
            }
            return views
        }
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (context != null && appWidgetManager != null && appWidgetIds != null) {
            (GithubTrendingRepositoryImp()).fetchTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GithubTrending>> {
                    override fun onError(e: Throwable?) {
                    }

                    override fun onNext(t: List<GithubTrending>?) {
                        if (t == null) return
                        val views = createWidgetView(context, t, *appWidgetIds)
                        appWidgetManager.updateAppWidget(appWidgetIds, views)
                    }

                    override fun onCompleted() {
                    }
                })
        }
    }
}