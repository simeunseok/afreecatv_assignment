package com.example.afreecatvassignment.data.gitrepository.remote

import com.example.afreecatvassignment.data.gitrepository.ResponseGitRepositoryList
import retrofit2.http.GET
import retrofit2.http.Query

interface GitRepositoryRemoteService {

    @GET("/search/repositories")
    suspend fun fetchGitRepositoryList(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ResponseGitRepositoryList

}
