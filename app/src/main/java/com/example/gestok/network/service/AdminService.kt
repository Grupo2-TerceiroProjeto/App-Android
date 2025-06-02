package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.admin.data.RegisterCreateData
import retrofit2.http.GET
import retrofit2.http.Path

import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.screens.internalScreens.admin.data.RegisterEditData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

interface AdminService {

    @GET("funcionarios/pegar-todos/{idEmpresa}")
    suspend fun getFuncionarios(@Path("idEmpresa") idEmpresa:Int): List<RegisterData>

    @POST("funcionarios/register")
    suspend fun post(@Body funcionario: RegisterCreateData): RegisterCreateData

    @PUT("funcionarios")
    suspend fun put(@Body funcionario: RegisterEditData) : RegisterEditData

    @DELETE("funcionarios/{idFuncionario}")
    suspend fun delete(@Path("idFuncionario") idFuncionario:Int)
}