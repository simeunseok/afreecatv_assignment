package com.example.afreecatvassignment.data.gitrepository

import com.google.gson.annotations.SerializedName

data class ResponseGitRepositoryList(
    val items: List<GitRepositoryItem>
)

data class GitRepositoryItem(
    @SerializedName("full_name")
    val fullName: String,
    val owner: GitRepositoryOwner,
    val language: String
)

data class GitRepositoryOwner(
    @SerializedName("avatar_url")
    val avatarUrl: String
)
