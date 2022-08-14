package com.etwicaksono.myzoo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etwicaksono.myzoo.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val actionBar = supportActionBar
        actionBar?.title = "About"
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}