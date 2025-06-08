package com.example.gestok.data.network

import com.example.gestok.application.config.ApiConfig
import com.example.gestok.data.repository.LingvaRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiLingva {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.LINGVA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun lingvaRepository(): LingvaRepository {
        return retrofit.create(LingvaRepository::class.java)
    }
}
