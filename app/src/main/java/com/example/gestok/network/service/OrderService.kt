package com.example.gestok.network.service

import com.example.gestok.components.orderpage.OrderData
import retrofit2.http.GET

interface OrderService {

    @GET("pedidos/buscar-todos")
    suspend fun getPedidos(): List<OrderData>
}