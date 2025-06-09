package com.example.gestok.data.network

import com.example.gestok.application.config.ApiConfig
import com.example.gestok.data.repository.CloudinaryRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCloudinary {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.CLOUDINARY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun cloudinaryRepository(): CloudinaryRepository {
        return retrofit.create(CloudinaryRepository::class.java)
    }
}
