package com.example.afreecatvassignment.ui.searchrepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ActivitySearchRepositoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchRepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchRepositoryBinding
    private val viewModel by viewModels<SearchRepositoryViewModel>()
    private val searchRepositoryAdapter = SearchRepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_repository)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setContentView(binding.root)
        setupToolbarMenuItemOnClickListener()
        setupRecyclerViewAdapter()
        collectRepositoryList()
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
        with(binding.rvSearchRepository) {
            adapter = searchRepositoryAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!canScrollVertically(1)) {
                        viewModel.fetchAndAddRepositoryList()
                    }
                }
            })
        }
    }

    private fun collectRepositoryList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repositoryList.collect { list ->
                    searchRepositoryAdapter.submitList(list)
                }
            }
        }
    }
}
