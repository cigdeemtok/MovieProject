package com.example.movieproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.databinding.LoadMoreBinding

class LoadMoreAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadMoreAdapter.ViewHolder>()
{

    private lateinit var binding : LoadMoreBinding


    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadMoreAdapter.ViewHolder {
        binding = LoadMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(retry)
    }

    override fun onBindViewHolder(holder: LoadMoreAdapter.ViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    inner class ViewHolder(retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnLoadMoreB.setOnClickListener { retry() }
        }

        fun setData(state : LoadState){
            binding.apply {
                loadingProgressBar.isVisible = state is LoadState.Loading
                alertRetryText.isVisible = state is LoadState.Error
                btnLoadMoreB.isVisible = state is LoadState.Error
            }
        }
    }


}