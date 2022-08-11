package com.etwicaksono.myzoo.ui.activity.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etwicaksono.myzoo.api.ApiConfig
import com.etwicaksono.myzoo.responses.ResponseAnimal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimalsListViewModel : ViewModel() {
    private val _listAnimals = MutableLiveData<List<ResponseAnimal>>()
    val listAnimals: LiveData<List<ResponseAnimal>> = _listAnimals
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAnimals() {
        _isLoading.value = true
        api.getAllAnimals().enqueue(object : Callback<List<ResponseAnimal>> {
            override fun onResponse(
                call: Call<List<ResponseAnimal>>,
                response: Response<List<ResponseAnimal>>
            ) {
                _isLoading.value=false
                _listAnimals.postValue(response.body())
            }

            override fun onFailure(call: Call<List<ResponseAnimal>>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG, "getAnimals onFailure: ${t.message.toString()}")
            }

        })
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