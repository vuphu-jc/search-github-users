package com.example.searchgithubusers.ui.activity.github_user_detail

import com.example.searchgithubusers.model.repository.GithubRepoRepository
import com.example.searchgithubusers.model.repository.GithubRepoRepositoryImp
import com.example.searchgithubusers.model.repository.GithubUserRepository
import com.example.searchgithubusers.model.repository.GithubUserRepositoryRetrofitImp
import com.example.searchgithubusers.ui.activity.search.SearchContract
import com.example.searchgithubusers.ui.activity.search.SearchPresenter
import com.example.searchgithubusers.utils.dagger.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class GithubUserDetailModule {

    @ActivityScope
    @Provides
    fun providerPresenter(githubRepoRepository: GithubRepoRepository):
            GithubUserDetailContract.Presenter {
        return GithubUserDetailPresenter().apply {
            mRepository = githubRepoRepository
        }
    }

    @ActivityScope
    @Provides
    fun providerGithubRepoRepository(): GithubRepoRepository {
        return GithubRepoRepositoryImp()
    }
}