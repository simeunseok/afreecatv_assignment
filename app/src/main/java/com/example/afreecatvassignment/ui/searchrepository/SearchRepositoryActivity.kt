package com.example.afreecatvassignment.ui.searchrepository

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ActivitySearchRepositoryBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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

        setupRecyclerViewAdapter()
        collectRepositoryList()
        collectNoMoreData()
        collectCantAccessNetwork()
    }

    private fun setupRecyclerViewAdapter() {
        with(binding.rvSearchRepository) {
            adapter = searchRepositoryAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!canScrollVertically(SCROLL_POSITIVE)) {
                        viewModel.fetchRepositoryListContinue()
                    }
                }
            })
        }
    }

    private fun collectRepositoryList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repositoryList.collect { list ->
                    viewModel.isEmpty.value = list.isEmpty()
                    searchRepositoryAdapter.submitList(list)
                }
            }
        }
    }

    private fun collectNoMoreData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noMoreData.collectLatest {
                    Snackbar.make(binding.root, getString(R.string.snackbar_noMoreData), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun collectCantAccessNetwork() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cantAccessNetwork.collectLatest {
                    Snackbar.make(binding.root, getString(R.string.snackbar_cantAccessNetwork), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    companion object {
        private const val SCROLL_POSITIVE = 1
    }
}
