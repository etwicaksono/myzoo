package com.etwicaksono.myzoo.ui.activity.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.etwicaksono.myzoo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val viewModel: AnimalsListViewModel by viewModels()

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
        }

        viewModel.apply {
            getAnimals()

            listAnimals.observe(this@HomeActivity) {
                if (it != null && it.isNotEmpty()) homeAdapter.setAnimalsListData(
                    it
                )
            }

            mainLoading.observe(this@HomeActivity) {
                binding.centerProgressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
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