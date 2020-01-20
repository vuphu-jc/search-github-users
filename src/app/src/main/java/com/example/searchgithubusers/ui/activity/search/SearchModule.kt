package com.example.searchgithubusers.ui.activity.search

import com.example.searchgithubusers.model.repository.GithubUserRepository
import com.example.searchgithubusers.model.repository.GithubUserRepositoryRetrofitImp
import com.example.searchgithubusers.utils.dagger.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class SearchModule() {

    @ActivityScope
    @Provides
    fun providerPresenter(githubUserRepository: GithubUserRepository): SearchContract.Presenter {
        return SearchPresenter().apply {
            mRepository = githubUserRepository
        }
    }

    @ActivityScope
    @Provides
    fun providerGithubUserRepository(): GithubUserRepository {
        return GithubUserRepositoryRetrofitImp()
    }
}