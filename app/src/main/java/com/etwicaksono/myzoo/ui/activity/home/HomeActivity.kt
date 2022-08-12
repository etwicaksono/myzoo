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
    private lateinit var homeAdapter: HomeAdapter
    private val viewModel: AnimalsListViewModel by viewModels()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        homeAdapter = HomeAdapter()
        binding.rvAnimals.apply {
            this.adapter = homeAdapter
            this.layoutManager = layoutManager
            addItemDecoration(itemDecoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val countItem = layoutManager.itemCount
                    val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition

                    if (isLastPosition && !isLoading) {
                        viewModel.addMore()
                    }
                }
            })
        }

        viewModel.apply {
            init()

            listAnimals.observe(this@HomeActivity) {
                if (it != null && it.isNotEmpty()) homeAdapter.setAnimalsListData(
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