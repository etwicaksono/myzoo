package com.etwicaksono.myzoo.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.etwicaksono.myzoo.R
import com.etwicaksono.myzoo.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}