package com.etwicaksono.myzoo.ui.activity.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.etwicaksono.myzoo.R
import com.etwicaksono.myzoo.databinding.ItemRowLoadingBinding

class AnimalLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<AnimalLoadStateAdapter.LoadStateViewHolder>() {
    class LoadStateViewHolder(parent: ViewGroup, retry: () -> Unit) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_loading, parent, false
        )
    ) {
        private val binding = ItemRowLoadingBinding.bind(itemView)
        private val progressAnimation = binding.progressCircular
        private val message = binding.progressMessage
        private val retry = binding.buttonRetry.also { it.setOnClickListener { retry() } }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                message.text = loadState.error.localizedMessage
            }

            progressAnimation.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            message.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        TODO("Not yet implemented")
    }
}