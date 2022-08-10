package com.etwicaksono.myzoo.api

import com.etwicaksono.myzoo.responses.ResponseAnimal
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("rand/10")
    fun getAllAnimals(): Call<List<ResponseAnimal>>
}