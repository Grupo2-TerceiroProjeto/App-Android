package com.example.gestok.network.service

import com.example.gestok.screens.login.data.LoginUser
import com.example.gestok.screens.login.data.SendEmail
import com.example.gestok.screens.login.data.SendEmailResponse
import com.example.gestok.screens.login.data.UserReset
import com.example.gestok.screens.login.data.UserSession
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {

    @POST("funcionarios/login")
    suspend fun login(@Body usuarioLogin: LoginUser): UserSession

    @PUT("funcionarios/redefinir-senha-funcionario")
    suspend fun redefinirSenha(@Body usuario: UserReset): Response<Unit>

    @POST("resetPassword")
    suspend fun enviarEmail(@Body email: SendEmail): SendEmailResponse

}