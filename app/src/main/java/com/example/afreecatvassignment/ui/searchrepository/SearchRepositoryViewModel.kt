package com.example.afreecatvassignment.ui.searchrepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteDataSource
import com.example.afreecatvassignment.ui.searchrepository.model.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private val gitRepositoryRemoteDataSource: GitRepositoryRemoteDataSource
) : ViewModel() {

    val keyword = MutableStateFlow("")
    private var currentPage = 1

    private val _repositoryList = MutableStateFlow<List<GitRepository>>(emptyList())
    val repositoryList: StateFlow<List<GitRepository>> = _repositoryList

    fun fetchRepositoryList() {
        currentPage = 1
        viewModelScope.launch {
            _repositoryList.value = gitRepositoryRemoteDataSource.fetchGitRepositoryList(this@SearchRepositoryViewModel.keyword.value, currentPage).run {
                items.map { item ->
                    GitRepository(item.fullName, item.owner.avatarUrl, item.language)
                }
            }
        }
    }

    fun fetchAndAddRepositoryList(keyword: String = this.keyword.value) {
        viewModelScope.launch {
            _repositoryList.value += gitRepositoryRemoteDataSource.fetchGitRepositoryList(this@SearchRepositoryViewModel.keyword.value, ++currentPage).run {
                items.map { item ->
                    GitRepository(item.fullName, item.owner.avatarUrl, item.language)
                }
            }
        }
    }

}
