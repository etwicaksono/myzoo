package com.etwicaksono.myzoo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.etwicaksono.myzoo.api.ApiService
import com.etwicaksono.myzoo.responses.Animal

class AnimalPagingSource(private val apiService: ApiService) : PagingSource<Int, Animal>() {
    override fun getRefreshKey(state: PagingState<Int, Animal>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Animal> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getAllAnimals()
            LoadResult.Page(
                data = response.body()!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}