package com.example.searchgithubusers.ui.activity.github_user_detail

import com.example.searchgithubusers.application.AppComponent
import com.example.searchgithubusers.ui.activity.main.MainModule
import com.example.searchgithubusers.utils.dagger.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [GithubUserDetailModule::class])
interface GithubUserDetailComponent {
    fun inject(githubUserDetailActivity: GithubUserDetailActivity?)
}