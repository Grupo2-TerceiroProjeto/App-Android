package com.example.gestok.network

import com.example.gestok.network.service.CloudinaryService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCloudinary {

    private const val CLOUD_NAME = "ddmjnxjm7"
    private const val BASE_URL = "https://api.cloudinary.com/v1_1/$CLOUD_NAME/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun cloudinaryService(): CloudinaryService {
        return retrofit.create(CloudinaryService::class.java)
    }
}
