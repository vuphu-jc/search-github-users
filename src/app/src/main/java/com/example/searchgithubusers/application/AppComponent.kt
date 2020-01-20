package com.example.searchgithubusers.application

import android.content.Context
import com.example.searchgithubusers.utils.dagger.AppScope
import dagger.Component

@AppScope
@Component(modules = [ApplicationModule::class])
interface AppComponent {
    fun applicationContext(): Context
}