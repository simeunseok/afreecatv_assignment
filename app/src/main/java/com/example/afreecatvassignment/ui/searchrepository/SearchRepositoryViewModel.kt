package com.example.afreecatvassignment.ui.searchrepository

import androidx.lifecycle.ViewModel
import com.example.afreecatvassignment.data.gitrepository.remote.GitRepositoryRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchRepositoryViewModel @Inject constructor(
    private val gitRepositoryRemoteDataSource: GitRepositoryRemoteDataSource
) : ViewModel() {



}
