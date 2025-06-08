package com.example.gestok.data.repository

import com.example.gestok.domain.model.auth.LoginUser
import com.example.gestok.domain.model.auth.SendEmail
import com.example.gestok.domain.model.auth.SendEmailResponse
import com.example.gestok.domain.model.auth.UserReset
import com.example.gestok.domain.model.auth.UserSession
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthRepository {

    @POST("funcionarios/login")
    suspend fun login(@Body usuarioLogin: LoginUser): UserSession

    @PUT("funcionarios/redefinir-senha-funcionario")
    suspend fun redefinirSenha(@Body usuario: UserReset): Response<Unit>

    @POST("resetPassword")
    suspend fun enviarEmail(@Body email: SendEmail): SendEmailResponse

}