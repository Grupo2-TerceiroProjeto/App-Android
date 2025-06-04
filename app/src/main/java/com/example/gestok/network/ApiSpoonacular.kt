package com.example.gestok.network

import com.example.gestok.network.service.SpoonacularService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiSpoonacular {

    private const val BASE_URL = "https://api.spoonacular.com/food/ingredients/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun spoonacularService(): SpoonacularService {
        return retrofit.create(SpoonacularService::class.java)
    }
}