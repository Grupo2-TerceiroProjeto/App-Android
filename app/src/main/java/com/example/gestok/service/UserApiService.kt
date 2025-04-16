package com.example.gestok.service

import com.example.gestok.screens.loginscreen.LoggedInUser
import com.example.gestok.screens.loginscreen.LoginUser
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface UserApiService {

    @POST("login")
    suspend fun login(@Body usuarioLogin: LoginUser): LoggedInUser

    object UsuarioApi {
        private const val BASE_URL = "http://10.18.12.70:8080/"

        val api: UserApiService by lazy {
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
                .create(UserApiService::class.java)
        }
    }
}