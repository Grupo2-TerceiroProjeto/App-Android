package com.example.gestok.network

import com.example.gestok.network.service.AuthService
import com.example.gestok.network.service.DashboardService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://192.168.15.157:8080/" //Mude para o IP da sua Máquina

    private val api by lazy {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        api.create(AuthService::class.java)
    }

    val dashboardService: DashboardService by lazy {
        api.create(DashboardService::class.java)
    }
}