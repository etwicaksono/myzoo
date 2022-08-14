package com.etwicaksono.myzoo.ui.activity.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.etwicaksono.myzoo.R
import com.etwicaksono.myzoo.databinding.ItemRowAnimalBinding
import com.etwicaksono.myzoo.responses.Animal
import com.etwicaksono.myzoo.ui.activity.DetailActivity

class AnimalPagerAdapter :
    PagingDataAdapter<Animal, AnimalPagerAdapter.ViewHolder>(AnimalComparator) {
    object AnimalComparator : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(val view: ItemRowAnimalBinding) : RecyclerView.ViewHolder(view.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = getItem(position)!!
        holder.view.apply {
            tvName.text = animal.name
            tvLatinName.text = animal.latinName

            Glide.with(ivAnimal.context)
                .load(animal.imageLink)
                .placeholder(R.drawable.default_image)
                .into(ivAnimal)
        }

        holder.view.itemRowAnimal.setOnClickListener {
            val detailIntent = Intent(it.context, DetailActivity::class.java)
            detailIntent.apply {
                putExtra("animal", animal)
            }
            it.context.startActivity(detailIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowAnimalBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
}