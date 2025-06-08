package com.example.gestok.data.repository

import com.example.gestok.domain.model.order.OrderCreateModel
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.domain.model.order.OrderEditModel
import com.example.gestok.domain.model.order.RecipeModel
import com.example.gestok.domain.model.product.IngredientsModel
import com.example.gestok.domain.model.product.ProductModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderRepository {

    @GET("pedidos/buscar-todos")
    suspend fun getPedidos(): List<OrderModel>

    @GET("produtos/pegar-todos/{idEmpresa}")
    suspend fun getProdutos(@Path("idEmpresa") idEmpresa:Int): List<ProductModel>

    @POST("pedidos")
    suspend fun post(@Body pedido: OrderCreateModel): OrderCreateModel

    @PUT("pedidos/{idPedido}")
    suspend fun put(@Body pedido: OrderEditModel, @Path("idPedido") idPedido:Int): OrderCreateModel

    @GET("receitas/listar")
    suspend fun getReceitas(): List<RecipeModel>

    @GET("ingredientes/all")
    suspend fun getIngredientes(): List<IngredientsModel>
}