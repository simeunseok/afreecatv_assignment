package com.example.afreecatvassignment.data.gitrepository.remote

import com.example.afreecatvassignment.data.gitrepository.ResponseGitRepositoryList
import javax.inject.Inject

class GitRepositoryRemoteDataSourceImpl @Inject constructor(
    private val gitRepositoryRemoteService: GitRepositoryRemoteService
) : GitRepositoryRemoteDataSource {

    override suspend fun fetchGitRepositoryList(q: String, page: Int, perPage: Int) =
        gitRepositoryRemoteService.fetchGitRepositoryList(q, page, perPage)
}
