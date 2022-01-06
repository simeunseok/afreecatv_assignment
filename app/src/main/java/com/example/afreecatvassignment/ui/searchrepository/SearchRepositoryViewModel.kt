package com.example.afreecatvassignment.ui.searchrepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteDataSource
import com.example.afreecatvassignment.ui.searchrepository.model.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private val gitRepositoryRemoteDataSource: GitRepositoryRemoteDataSource
) : ViewModel() {

    val keyword = MutableStateFlow("")

    private val repositoryList = MutableStateFlow<List<GitRepository>>(emptyList())

    fun fetchRepositoryList(keyword: String = this.keyword.value) {
        viewModelScope.launch {
            repositoryList.value = gitRepositoryRemoteDataSource.fetchGitRepositoryList(keyword, 1).run {
                this.items.map { item ->
                    GitRepository(item.fullName, item.owner.avatarUrl, item.language)
                }
            }
        }
    }

}
