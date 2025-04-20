package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.OrderData
import retrofit2.http.GET

interface DashboardService {

    @GET("pedidos/buscar-todos")
    suspend fun getPedidos(): List<OrderData>

}