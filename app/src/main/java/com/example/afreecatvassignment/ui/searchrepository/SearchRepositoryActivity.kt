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
    private val adapter = SearchRepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_repository)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setContentView(binding.root)
        setupToolbarMenuItemOnClickListener()
        setupRecyclerViewAdapter()
    }

    private fun setupToolbarMenuItemOnClickListener() {
        binding.toolbarSearchRepository.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menu_search_repository) {
                viewModel.fetchRepositoryList()
                true
            } else {
                false
            }
        }
    }

    private fun setupRecyclerViewAdapter() {
        binding.rvSearchRepository.adapter = adapter
    }
}
