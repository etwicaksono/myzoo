package com.etwicaksono.myzoo.ui.activity.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etwicaksono.myzoo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}