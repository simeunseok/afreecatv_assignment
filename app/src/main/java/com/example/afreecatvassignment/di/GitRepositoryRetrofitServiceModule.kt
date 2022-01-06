package com.example.afreecatvassignment.di

import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GitRepositoryRetrofitServiceModule {

    @Provides
    @Singleton
    fun provideGitRepositoryService(retrofit: Retrofit): GitRepositoryRemoteService =
        retrofit.create(GitRepositoryRemoteService::class.java)
}
