package com.example.afreecatvassignment.ui.searchrepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ActivitySearchRepositoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchRepositoryBinding
    private val viewModel by viewModels<SearchRepositoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_repository)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setContentView(binding.root)
    }
}
