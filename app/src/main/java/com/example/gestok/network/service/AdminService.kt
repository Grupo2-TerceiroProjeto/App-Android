package com.example.gestok.network.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

import com.example.gestok.screens.internalScreens.admin.data.RegisterData

interface AdminService {

    @GET("funcionarios/pegar-todos/{idEmpresa}")
    suspend fun getFuncionarios(@Path("idEmpresa") idEmpresa:Int): List<RegisterData>


}