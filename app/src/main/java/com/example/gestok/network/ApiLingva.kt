package com.example.gestok.network

import com.example.gestok.network.service.LingvaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiLingva {

    private const val BASE_URL = "https://lingva.ml/api/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun lingvaService(): LingvaService {
        return retrofit.create(LingvaService::class.java)
    }
}
