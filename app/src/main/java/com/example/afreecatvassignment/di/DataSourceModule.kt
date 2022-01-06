package com.example.afreecatvassignment.di

import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteDataSource
import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindGitRepository(dataSource: GitRepositoryRemoteDataSourceImpl): GitRepositoryRemoteDataSource
}
