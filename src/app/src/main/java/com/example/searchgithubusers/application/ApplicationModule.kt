package com.example.searchgithubusers.application

import android.content.Context
import com.example.searchgithubusers.utils.dagger.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@AppScope
@Module
class ApplicationModule(context: Context) {

    private val mContext: Context = context

    @Provides
    public fun provideApplicationContext(): Context {
        return mContext
    }
}