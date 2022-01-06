package com.example.afreecatvassignment.ui.searchrepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.afreecatvassignment.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRepositoryActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchRepositoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_repository)
    }
}
