package com.example.gestok.data.repository

import com.example.gestok.domain.model.order.OrderModel
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardRepository {

    @GET("pedidos/buscar-todos")
    suspend fun getPedidos(): List<OrderModel>

    @GET("avaliacoes/listar-todos/{idEmpresa}")
    suspend fun getAvaliacoes(@Path("idEmpresa") idEmpresa:Int): Response<ResponseBody>

}