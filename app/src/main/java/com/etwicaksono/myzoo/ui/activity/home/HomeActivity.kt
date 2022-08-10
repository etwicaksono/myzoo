package com.etwicaksono.myzoo.ui.activity.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.etwicaksono.myzoo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: HomeAdapter
    private val viewModel: AnimalsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        adapter = HomeAdapter()
        binding.rvAnimals.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
            addItemDecoration(itemDecoration)
        }

        viewModel.apply {
            getAnimals()
            listAnimals.observe(this@HomeActivity) { listAnimals ->
                if (listAnimals != null && listAnimals.isNotEmpty()) adapter.setAnimalsListData(
                    listAnimals
                )
            }
        }
    }
}