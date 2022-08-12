package com.etwicaksono.myzoo.ui.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.etwicaksono.myzoo.R
import com.etwicaksono.myzoo.databinding.ActivityDetailBinding
import com.etwicaksono.myzoo.responses.ResponseAnimal

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val animalList = intent.getParcelableArrayListExtra<ResponseAnimal>("animalList")
        val position = intent.getIntExtra("position", 0)
        val animal = animalList?.get(position)

        binding.apply {
            Glide.with(this@DetailActivity)
                .load(animal?.imageLink)
                .placeholder(R.drawable.default_image)
                .into(ivAnimal)

            tvName.text = animal?.name
            tvLatinName.text = animal?.latinName
            tvType.text = animal?.animalType
            tvActiveTime.text = animal?.activeTime
            tvLengthMin.text = "${animal?.lengthMin} ft"
            tvLengthMax.text = "${animal?.lengthMax} ft"
            tvWeightMin.text = "${animal?.weightMin} lbs"
            tvWeightMax.text = "${animal?.weightMax} lbs"
            tvLifespan.text = animal?.lifespan + if(animal?.lifespan?.toInt()?.equals(1) == true) "years" else "year"
            tvHabitat.text = animal?.habitat
            tvDiet.text = animal?.diet
            tvGeoRange.text = animal?.geoRange
        }
    }
}