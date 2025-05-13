package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.order.data.OrderCreateData
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.ProductData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {

    @GET("pedidos/buscar-todos")
    suspend fun getPedidos(): List<OrderData>

    @GET("produtos/pegar-todos/{idEmpresa}")
    suspend fun getProdutos(@Path("idEmpresa") idEmpresa:Int): List<ProductData>

    @POST("produtos")
    suspend fun postPedido(@Body pedido: OrderCreateData): OrderCreateData
}