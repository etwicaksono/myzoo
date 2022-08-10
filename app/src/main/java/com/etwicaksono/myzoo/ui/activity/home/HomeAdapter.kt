package com.etwicaksono.myzoo.ui.activity.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.etwicaksono.myzoo.R
import com.etwicaksono.myzoo.databinding.ItemRowAnimalBinding
import com.etwicaksono.myzoo.responses.ResponseAnimal

class HomeAdapter:RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val binding = ItemRowAnimalBinding.bind(view)
    }

    private val listAnimals = ArrayList<ResponseAnimal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_animal,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text=listAnimals[position].name
            tvLatinName.text=listAnimals[position].latinName
            Glide.with(ivAnimal.context).load(listAnimals[position].imageLink).into(ivAnimal)
        }
    }

    override fun getItemCount(): Int {
        return listAnimals.size
    }
}