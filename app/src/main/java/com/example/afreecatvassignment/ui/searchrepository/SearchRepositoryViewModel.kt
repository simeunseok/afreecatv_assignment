package com.example.afreecatvassignment.ui.searchrepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteDataSource
import com.example.afreecatvassignment.ui.searchrepository.model.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private val gitRepositoryRemoteDataSource: GitRepositoryRemoteDataSource
) : ViewModel() {

    val keyword = MutableStateFlow("")

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    val isEmpty = MutableStateFlow(false)

    private val _repositoryList = MutableStateFlow<List<GitRepository>>(emptyList())
    val repositoryList: StateFlow<List<GitRepository>> = _repositoryList

    private var currentPage = 1
    private var searchDebounceJob: Job = Job()
    private var continueDebounceJob: Job = Job()

    private suspend fun fetchRepositoryList(page: Int) =
        gitRepositoryRemoteDataSource.fetchGitRepositoryList(keyword.value, page).run {
            items.map { item ->
                GitRepository(item.fullName, item.owner.avatarUrl, item.language)
            }
        }

    fun fetchRepositoryListNewKeyword() {
        currentPage = 1
        searchDebounceJob.cancel()
        searchDebounceJob = viewModelScope.launch {
            delay(DEBOUNCE_LIMIT)
            _repositoryList.value = fetchRepositoryList(currentPage)
        }
    }

    fun fetchRepositoryListContinue() {
        continueDebounceJob.cancel()
        continueDebounceJob = viewModelScope.launch {
            _isSearching.value = true
            delay(DEBOUNCE_LIMIT)
            _repositoryList.value += fetchRepositoryList(++currentPage)
            _isSearching.value = false
        }
    }

    companion object {
        private const val DEBOUNCE_LIMIT = 500L
    }
}
