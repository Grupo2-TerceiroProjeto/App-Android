package com.example.gestok.network.service

import com.example.gestok.screens.login.data.LoginUser
import com.example.gestok.screens.login.data.UserSession
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("funcionarios/login")
    suspend fun login(@Body usuarioLogin: LoginUser): UserSession

}