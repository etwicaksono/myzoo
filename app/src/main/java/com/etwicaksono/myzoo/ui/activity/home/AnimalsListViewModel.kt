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
import com.etwicaksono.myzoo.responses.Animal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimalsListViewModel : ViewModel() {
    private val _listAnimals = MutableLiveData<MutableList<Animal>>()
    val listAnimals: LiveData<MutableList<Animal>> = _listAnimals
    private val _mainLoading = MutableLiveData<Boolean>()
    val mainLoading: LiveData<Boolean> = _mainLoading
    private val _refreshLoading = MutableLiveData<Boolean>()
    val refreshLoading: LiveData<Boolean> = _refreshLoading

    fun init() {
        _mainLoading.value = true
        api.getAllAnimals().enqueue(object : Callback<MutableList<Animal>> {
            override fun onResponse(
                call: Call<MutableList<Animal>>,
                response: Response<MutableList<Animal>>
            ) {
                _mainLoading.value = false
                _listAnimals.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<Animal>>, t: Throwable) {
                _mainLoading.value = false
                Log.e(TAG, "getAnimals onFailure: ${t.message.toString()}")
            }

        })
    }

    fun addMore() {
        _refreshLoading.value = true
        api.getAllAnimals().enqueue(object : Callback<MutableList<Animal>> {
            override fun onResponse(
                call: Call<MutableList<Animal>>,
                response: Response<MutableList<Animal>>
            ) {
                _refreshLoading.value = false
                _listAnimals.postValue(response.body())

                val responseData = response.body()?.toMutableList()
                if(responseData!=null){
                    _listAnimals.value?.addAll(responseData)
                    _listAnimals.postValue(_listAnimals.value)
                }
            }

            override fun onFailure(call: Call<MutableList<Animal>>, t: Throwable) {
                _refreshLoading.value = false
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