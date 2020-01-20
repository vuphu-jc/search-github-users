package com.example.searchgithubusers.ui.activity.search

import com.example.searchgithubusers.application.AppComponent
import com.example.searchgithubusers.utils.dagger.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [SearchModule::class])
interface SearchComponent {
    fun inject(searchActivity: SearchActivity?)
}