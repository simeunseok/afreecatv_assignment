package com.example.afreecatvassignment.ui.searchrepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteDataSource
import com.example.afreecatvassignment.ui.searchrepository.model.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private val gitRepositoryRemoteDataSource: GitRepositoryRemoteDataSource
) : ViewModel() {

    val keyword = MutableStateFlow("")

    val isEmpty = MutableStateFlow(false)

    private val _isSearch = MutableStateFlow(false)
    val isSearch: StateFlow<Boolean> = _isSearch

    private val _isSearchingNextPage = MutableStateFlow(false)
    val isSearchNextPage: StateFlow<Boolean> = _isSearchingNextPage

    private val _noMoreData = MutableSharedFlow<Unit>()
    val noMoreData: SharedFlow<Unit> = _noMoreData

    private val _cantAccessNetwork = MutableSharedFlow<Unit>()
    var cantAccessNetwork: SharedFlow<Unit> = _cantAccessNetwork

    private val _repositoryList = MutableStateFlow<List<GitRepository>>(emptyList())
    val repositoryList: StateFlow<List<GitRepository>> = _repositoryList

    private var currentPage = 1
    private var searchDebounceJob: Job = Job()
    private var loadingFlag = AtomicBoolean(false)

    private suspend fun fetchRepositoryList(page: Int) =
        gitRepositoryRemoteDataSource.fetchGitRepositoryList(keyword.value, page)?.run {
            items.map { item ->
                GitRepository(item.fullName, item.owner.avatarUrl, item.language)
            }
        }

    fun fetchRepositoryListNewKeyword() {
        currentPage = 1
        searchDebounceJob.cancel()
        searchDebounceJob = viewModelScope.launch {
            _isSearch.value = true
            delay(DEBOUNCE_LIMIT)

            if (keyword.value.isNotBlank()) {
                when (val result = fetchRepositoryList(currentPage)) {
                    null -> _cantAccessNetwork.emit(Unit)
                    else -> _repositoryList.value = result
                }
            }

            _isSearch.value = false
        }
    }

    fun fetchRepositoryListContinue() {
        viewModelScope.launch {
            if (loadingFlag.get()) {
                return@launch
            }
            loadingFlag.set(true)
            _isSearchingNextPage.value = true

            when (val result = fetchRepositoryList(currentPage + 1)) {
                null -> _cantAccessNetwork.emit(Unit)
                isEmpty -> _noMoreData.emit(Unit)
                else -> {
                    _repositoryList.value += result
                    currentPage++
                }
            }
            _isSearchingNextPage.value = false
            loadingFlag.set(false)
        }
    }

    companion object {
        private const val DEBOUNCE_LIMIT = 500L
    }
}
