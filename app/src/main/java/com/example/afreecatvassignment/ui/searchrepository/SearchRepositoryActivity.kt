package com.example.afreecatvassignment.ui.searchrepository

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ActivitySearchRepositoryBinding
import com.example.afreecatvassignment.util.launchAndRepeatWithLifecycle
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
        setupToolbarMenuItemOnClickListener()

        launchAndRepeatWithLifecycle {
            launch { collectRepositoryList() }
            launch { collectSearchEvent() }
            launch { collectNoMoreData() }
            launch { collectCantAccessNetwork() }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        currentFocus?.run {
            if ((ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && this is EditText) {
                val screenSpot = IntArray(2)
                getLocationOnScreen(screenSpot)
                val x: Float = ev.rawX + left - screenSpot[0]
                val y: Float = ev.rawY + top - screenSpot[1]
                if (x < left || x > right || y < top || y > bottom) {
                    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        window.decorView.applicationWindowToken, 0
                    )
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setupToolbarMenuItemOnClickListener() {
        binding.toolbarSearchRepository.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menu_search_repository) {
                viewModel.emitSearchEvent()
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

                    if (!canScrollVertically(SCROLL_POSITIVE)) {
                        viewModel.fetchRepositoryListContinue()
                    }
                }
            })
        }
    }

    private suspend fun collectRepositoryList() {
        viewModel.repositoryList.collect { list ->
            viewModel.isEmpty.value = list.isEmpty()
            searchRepositoryAdapter.submitList(list)
        }
    }

    private suspend fun collectSearchEvent() {
        var lastTime = 0L
        viewModel.searchEvent.collect {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= PERIODMILLIS) {
                lastTime = currentTime
                viewModel.fetchRepositoryListNewKeyword(0L)
            }
        }
    }

    private suspend fun collectNoMoreData() {
        viewModel.noMoreData.collectLatest {
            Snackbar.make(binding.root, getString(R.string.snackbar_noMoreData), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private suspend fun collectCantAccessNetwork() {
        viewModel.cantAccessNetwork.collectLatest {
            Snackbar.make(binding.root, getString(R.string.snackbar_cantAccessNetwork), Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val SCROLL_POSITIVE = 1
        private const val PERIODMILLIS = 500L
    }
}
