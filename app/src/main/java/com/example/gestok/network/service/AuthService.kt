package com.example.gestok.network.service

import com.example.gestok.screens.login.LoginUser
import com.example.gestok.screens.login.UserSession
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("funcionarios/login")
    suspend fun login(@Body usuarioLogin: LoginUser): UserSession

}