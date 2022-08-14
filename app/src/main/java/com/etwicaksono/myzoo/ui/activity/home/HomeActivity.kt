package com.etwicaksono.myzoo.ui.activity.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etwicaksono.myzoo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var animalPagerAdapter: AnimalPagerAdapter
    private val viewModel: AnimalsListViewModel by viewModels()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        animalPagerAdapter = AnimalPagerAdapter()
        binding.rvAnimals.apply {
            this.adapter = animalPagerAdapter
            this.layoutManager = layoutManager
            addItemDecoration(itemDecoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val countItem = layoutManager.itemCount
                    val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition

                    if (isLastPosition && !isLoading && dy > 0) {
                        viewModel.addMore()
                    }
                }
            })
        }

        viewModel.apply {
            init()

            listAnimals.observe(this@HomeActivity) {
                if (it != null && it.isNotEmpty()) animalPagerAdapter.setAnimalsListData(
                    it
                )
            }

            mainLoading.observe(this@HomeActivity) {
                binding.centerProgressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
                isLoading = it
            }
            refreshLoading.observe(this@HomeActivity) {
                binding.bottomProgressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
                isLoading = it
            }

            hasInternet(this@HomeActivity).observe(this@HomeActivity) {
                if (it == false) {
                    Toast.makeText(this@HomeActivity, "Internet unavailable", Toast.LENGTH_LONG)
                        .show()
                    binding.centerProgressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}