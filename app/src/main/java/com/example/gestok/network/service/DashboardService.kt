package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.order.data.OrderData
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardService {

    @GET("pedidos/buscar-todos")
    suspend fun getPedidos(): List<OrderData>

    @GET("avaliacoes/listar-todos/{idEmpresa}")
    suspend fun getAvaliacoes(@Path("idEmpresa") idEmpresa:Int): Response<ResponseBody>

}