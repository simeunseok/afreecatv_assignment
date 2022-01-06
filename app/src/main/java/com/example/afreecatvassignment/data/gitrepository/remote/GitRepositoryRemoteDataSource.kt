package com.example.afreecatvassignment.data.gitrepository.remote

import com.example.afreecatvassignment.data.gitrepository.ResponseGitRepositoryList
import com.google.gson.annotations.SerializedName

interface GitRepositoryRemoteDataSource {

    suspend fun fetchGitRepositoryList(q: String, page: Int, perPage: Int): ResponseGitRepositoryList

}
