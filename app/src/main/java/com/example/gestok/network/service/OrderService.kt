package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.order.data.IngredientsData
import com.example.gestok.screens.internalScreens.order.data.OrderCreateData
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.OrderEditData
import com.example.gestok.screens.internalScreens.order.data.ProductData
import com.example.gestok.screens.internalScreens.order.data.RecipeData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {

    @GET("pedidos/buscar-todos")
    suspend fun getPedidos(): List<OrderData>

    @GET("produtos/pegar-todos/{idEmpresa}")
    suspend fun getProdutos(@Path("idEmpresa") idEmpresa:Int): List<ProductData>

    @POST("pedidos")
    suspend fun post(@Body pedido: OrderCreateData): OrderCreateData

    @PUT("pedidos/{idPedido}")
    suspend fun put(@Body pedido: OrderEditData, @Path("idPedido") idPedido:Int): OrderCreateData

    @GET("receitas/listar")
    suspend fun getReceitas(): List<RecipeData>

    @GET("ingredientes/all")
    suspend fun getIngredientes(): List<IngredientsData>
}