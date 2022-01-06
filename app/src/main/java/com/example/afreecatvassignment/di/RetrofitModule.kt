package com.example.afreecatvassignment.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val GITHUB_URL = "http://api.github.com"

    @Provides
    @Singleton
    fun provideGitRepositoryRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

}
