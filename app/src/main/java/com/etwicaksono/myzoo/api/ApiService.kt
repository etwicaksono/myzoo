package com.etwicaksono.myzoo.api

import com.etwicaksono.myzoo.responses.ResponseAnimal
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("animals/rand/10")
    suspend fun getAllAnimals(): Response<List<ResponseAnimal>>
}