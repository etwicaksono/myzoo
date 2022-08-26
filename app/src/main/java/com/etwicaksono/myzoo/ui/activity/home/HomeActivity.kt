package com.etwicaksono.myzoo.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.etwicaksono.myzoo.AnimalViewModelFactory
import com.etwicaksono.myzoo.R
import com.etwicaksono.myzoo.api.ApiConfig
import com.etwicaksono.myzoo.databinding.ActivityHomeBinding
import com.etwicaksono.myzoo.repository.AnimalRepository
import com.etwicaksono.myzoo.ui.activity.AboutActivity
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
        val animalRepository = AnimalRepository(apiService)
        binding.rvAnimals.adapter =
            animalPagerAdapter.withLoadStateFooter(AnimalLoadStateAdapter(animalPagerAdapter::retry))
        binding.rvAnimals.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(
            this,
            AnimalViewModelFactory(animalRepository)
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

            binding.centerProgressBar.isVisible = firstLoading
            if (loadState.refresh is LoadState.NotLoading ||
                loadState.append is LoadState.NotLoading
            ) {
                firstLoading = false
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}