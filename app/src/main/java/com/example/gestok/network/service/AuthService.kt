package com.example.gestok.network.service

import com.example.gestok.screens.login.LoggedInUser
import com.example.gestok.screens.login.LoginUser
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("funcionarios/login")
    suspend fun login(@Body usuarioLogin: LoginUser): LoggedInUser

}