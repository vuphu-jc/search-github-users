package com.example.searchgithubusers.ui.activity.main

import com.example.searchgithubusers.application.AppComponent
import com.example.searchgithubusers.utils.dagger.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MainModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity?)
}