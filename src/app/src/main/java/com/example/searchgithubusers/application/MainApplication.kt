package com.example.searchgithubusers.application

import android.app.Application
import dagger.Component
import dagger.android.DaggerApplication

class MainApplication: Application() {

    private var mAppComponent: AppComponent? = null
    fun getAppComponent(): AppComponent {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
        }
        return mAppComponent as AppComponent
    }
}