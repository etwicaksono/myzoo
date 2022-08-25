package com.etwicaksono.myzoo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.etwicaksono.myzoo.repository.AnimalRepository
import com.etwicaksono.myzoo.ui.activity.home.AnimalsListViewModel

class AnimalViewModelFactory constructor(private val repository: AnimalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AnimalsListViewModel::class.java)) {
            AnimalsListViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}