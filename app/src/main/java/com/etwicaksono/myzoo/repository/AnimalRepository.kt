package com.etwicaksono.myzoo.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.etwicaksono.myzoo.api.ApiService
import com.etwicaksono.myzoo.paging.AnimalPagingSource
import com.etwicaksono.myzoo.responses.Animal
import com.etwicaksono.myzoo.responses.NETWORK_PAGE_SIZE

class AnimalRepository constructor(private val apiService: ApiService) {
    fun getAllAnimals(): LiveData<PagingData<Animal>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = { AnimalPagingSource(apiService) },
            initialKey = 1
        ).liveData
    }
}