package com.etwicaksono.myzoo.helper

import androidx.recyclerview.widget.DiffUtil
import com.etwicaksono.myzoo.responses.ResponseAnimal

class AnimalsDiffCallback(
    private val mOldAnimalsList: List<ResponseAnimal>,
    private val mNewAnimalsList: List<ResponseAnimal>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldAnimalsList.size
    }

    override fun getNewListSize(): Int {
        return mNewAnimalsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldAnimalsList[oldItemPosition].name == mNewAnimalsList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAnimal = mOldAnimalsList[oldItemPosition]
        val newAnimal = mOldAnimalsList[newItemPosition]
        return oldAnimal.id == newAnimal.id
    }

}