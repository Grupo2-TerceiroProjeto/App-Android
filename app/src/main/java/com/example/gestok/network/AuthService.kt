package com.example.gestok.network

import com.example.gestok.data.LoggedInUser
import com.example.gestok.data.LoginUser
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface AuthService {

    @POST("funcionarios/login")
    suspend fun login(@Body usuarioLogin: LoginUser): LoggedInUser

    object UsuarioApi {

        private const val BASE_URL = "http://192.168.15.157:8080/"//Mudar para o IP atual da sua Máquina

        val api: AuthService by lazy {
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
                .create(AuthService::class.java)
        }
    }
}