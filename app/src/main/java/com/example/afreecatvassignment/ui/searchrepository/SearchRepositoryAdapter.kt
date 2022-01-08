package com.example.afreecatvassignment.ui.searchrepository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecatvassignment.BR
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ItemRepositoryBinding
import com.example.afreecatvassignment.ui.searchrepository.model.GitRepository

class SearchRepositoryAdapter : ListAdapter<GitRepository, SearchRepositoryAdapter.SearchRepositoryViewHolder>(GitRepository.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepositoryViewHolder {
        val binding: ItemRepositoryBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_repository, parent, false)
        return SearchRepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchRepositoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class SearchRepositoryViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GitRepository) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}
