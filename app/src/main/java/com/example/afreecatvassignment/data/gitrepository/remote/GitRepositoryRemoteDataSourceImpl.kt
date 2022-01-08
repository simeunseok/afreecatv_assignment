package com.example.afreecatvassignment.data.gitrepository.remote

import javax.inject.Inject

class GitRepositoryRemoteDataSourceImpl @Inject constructor(
    private val gitRepositoryRemoteService: GitRepositoryRemoteService
) : GitRepositoryRemoteDataSource {

    override suspend fun fetchGitRepositoryList(q: String, page: Int, perPage: Int) = kotlin.runCatching {
        gitRepositoryRemoteService.fetchGitRepositoryList(q, page, perPage)
    }.getOrNull()
}
