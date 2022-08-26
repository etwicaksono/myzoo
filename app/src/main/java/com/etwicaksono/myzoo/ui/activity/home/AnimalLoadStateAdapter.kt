package com.etwicaksono.myzoo.ui.activity.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class AnimalLoadStateAdapter(private val retry:()->Unit):LoadStateAdapter<AnimalLoadStateAdapter.LoadStateViewHolder>() {
    class LoadStateViewHolder(parent: ViewGroup,retry: () -> Unit):RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate()) {

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        TODO("Not yet implemented")
    }
}