package com.example.afreecatvassignment.data.gitrepository.remote

import com.example.afreecatvassignment.data.gitrepository.ResponseGitRepositoryList

interface GitRepositoryRemoteDataSource {

    suspend fun fetchGitRepositoryList(q: String, page: Int, perPage: Int = 10): ResponseGitRepositoryList?

}
