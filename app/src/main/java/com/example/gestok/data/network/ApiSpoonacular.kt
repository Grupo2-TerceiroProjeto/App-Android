package com.example.gestok.data.network

import com.example.gestok.application.config.ApiConfig
import com.example.gestok.data.repository.SpoonacularRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiSpoonacular {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.SPOONACULAR_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun spoonacularRepository(): SpoonacularRepository {
        return retrofit.create(SpoonacularRepository::class.java)
    }
}