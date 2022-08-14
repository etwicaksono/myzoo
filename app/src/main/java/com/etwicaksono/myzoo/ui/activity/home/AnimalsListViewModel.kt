package com.etwicaksono.myzoo.ui.activity.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.etwicaksono.myzoo.api.ApiConfig
import com.etwicaksono.myzoo.repository.MainRepository
import com.etwicaksono.myzoo.responses.Animal

class AnimalsListViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage=MutableLiveData<String>()

    fun getAnimalList():LiveData<PagingData<Animal>>{
        return mainRepository.getAllAnimals().cachedIn(viewModelScope)
    }


    fun hasInternet(context: Context): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        result.value = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return result
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return result
            when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> result.value =
                    true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> result.value =
                    true
                else -> result.value = false
            }
            return result
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return result
            result.value = networkInfo.isConnected
            return result
        }
    }

    companion object {
        private val TAG = AnimalsListViewModel::class.java.simpleName
        private val api = ApiConfig.getApiService()
    }
}