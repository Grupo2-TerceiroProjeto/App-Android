package com.example.gestok.data.repository

import com.example.gestok.domain.model.admin.RegisterCreateModel
import retrofit2.http.GET
import retrofit2.http.Path

import com.example.gestok.domain.model.admin.RegisterModel
import com.example.gestok.domain.model.admin.RegisterEditModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

interface AdminRepository {

    @GET("funcionarios/pegar-todos/{idEmpresa}")
    suspend fun getFuncionarios(@Path("idEmpresa") idEmpresa:Int): List<RegisterModel>

    @POST("funcionarios/register")
    suspend fun post(@Body funcionario: RegisterCreateModel): RegisterCreateModel

    @PUT("funcionarios")
    suspend fun put(@Body funcionario: RegisterEditModel) : RegisterEditModel

    @DELETE("funcionarios/{idFuncionario}")
    suspend fun delete(@Path("idFuncionario") idFuncionario:Int)
}