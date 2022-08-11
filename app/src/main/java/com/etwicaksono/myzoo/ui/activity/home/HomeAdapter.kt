package com.etwicaksono.myzoo.ui.activity.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.etwicaksono.myzoo.R
import com.etwicaksono.myzoo.databinding.ItemRowAnimalBinding
import com.etwicaksono.myzoo.helper.AnimalsDiffCallback
import com.etwicaksono.myzoo.responses.ResponseAnimal
import com.etwicaksono.myzoo.ui.activity.detail.DetailActivity

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRowAnimalBinding.bind(view)
    }

    private val listAnimals = ArrayList<ResponseAnimal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_animal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = listAnimals[position].name
            tvLatinName.text = listAnimals[position].latinName

            Glide.with(ivAnimal.context)
                .load(listAnimals[position].imageLink)
                .placeholder(R.drawable.default_image)
                .into(ivAnimal)
        }

        holder.binding.itemRowAnimal.setOnClickListener {
            val detailIntent = Intent(it.context, DetailActivity::class.java)
            detailIntent.apply {
                putExtra("animalList", listAnimals)
                putExtra("position", position)
            }
            it.context.startActivity(detailIntent)
        }
    }

    override fun getItemCount(): Int {
        return listAnimals.size
    }

    fun setAnimalsListData(listAnimals: List<ResponseAnimal>) {
        val diffCallback = AnimalsDiffCallback(this.listAnimals, listAnimals)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listAnimals.clear()
        this.listAnimals.addAll(listAnimals)
        diffResult.dispatchUpdatesTo(this)
    }
}