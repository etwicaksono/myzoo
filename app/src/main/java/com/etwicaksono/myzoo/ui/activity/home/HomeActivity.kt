package com.etwicaksono.myzoo.ui.activity.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.etwicaksono.myzoo.MyViewModelFactory
import com.etwicaksono.myzoo.api.ApiConfig
import com.etwicaksono.myzoo.databinding.ActivityHomeBinding
import com.etwicaksono.myzoo.repository.MainRepository
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val animalPagerAdapter = AnimalPagerAdapter()
    private lateinit var viewModel: AnimalsListViewModel
    private var firstLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.getApiService()
        val mainRepository = MainRepository(apiService)
        binding.rvAnimals.adapter = animalPagerAdapter
        binding.rvAnimals.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[AnimalsListViewModel::class.java]

        viewModel.apply {

            errorMessage.observe(this@HomeActivity) {
                Toast.makeText(this@HomeActivity, it, Toast.LENGTH_SHORT).show()
            }

            hasInternet(this@HomeActivity).observe(this@HomeActivity) {
                if (it == false) {
                    Toast.makeText(this@HomeActivity, "Internet unavailable", Toast.LENGTH_LONG)
                        .show()
                    binding.centerProgressBar.visibility = View.INVISIBLE
                }
            }
        }

        animalPagerAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                if (firstLoading) {
                    binding.centerProgressBar.isVisible = true
                } else {
                    binding.bottomProgressBar.isVisible = true
                }
            } else {
                firstLoading = false
                binding.centerProgressBar.isVisible = false
                binding.bottomProgressBar.isVisible = false
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }

        lifecycleScope.launch {
            viewModel.getAnimalList().observe(this@HomeActivity) {
                it?.let {
                    animalPagerAdapter.submitData(lifecycle, it)
                }
            }
        }
    }
}