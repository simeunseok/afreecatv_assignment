package com.example.afreecatvassignment.ui.searchrepository.model

import androidx.recyclerview.widget.DiffUtil

data class GitRepository(
    val name: String,
    val avatarUrl: String,
    val language: String?,
) {
    companion object {
        val diffUtil by lazy {
            object : DiffUtil.ItemCallback<GitRepository>() {
                override fun areItemsTheSame(oldItem: GitRepository, newItem: GitRepository) = oldItem.name == newItem.name

                override fun areContentsTheSame(oldItem: GitRepository, newItem: GitRepository) = oldItem == newItem
            }
        }
    }
}
