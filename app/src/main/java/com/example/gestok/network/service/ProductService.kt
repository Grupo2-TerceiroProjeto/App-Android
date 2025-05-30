package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.order.data.RecipeBody
import com.example.gestok.screens.internalScreens.order.data.RecipeData
import com.example.gestok.screens.internalScreens.product.data.CategoryData
import com.example.gestok.screens.internalScreens.product.data.IngredientsData
import com.example.gestok.screens.internalScreens.product.data.ProductCreateData
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.screens.internalScreens.product.data.ProductEditData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {

    @GET("produtos/pegar-todos/{idEmpresa}")
    suspend fun getProdutos(@Path("idEmpresa") idEmpresa:Int): List<ProductData>

    @GET("categorias/all")
    suspend fun getCategorias(): List<CategoryData>

    @DELETE("produtos/{idProduto}")
    suspend fun delete(@Path("idProduto") idProduto:Int)

    @GET("ingredientes/all")
    suspend fun getIngredientes(): List<IngredientsData>

    @GET("receitas/listar")
    suspend fun getReceitas(): List<RecipeData>

    @POST("receitas")
    suspend fun postReceita(@Body receita: RecipeBody): RecipeBody

    @PUT("receitas/{id}")
    suspend fun putReceita( @Path("id") id:Int, @Body receita: RecipeBody): RecipeBody

    @POST("produtos/{empresaId}")
    suspend fun post(@Body produto: ProductCreateData, @Path("empresaId") empresaId:Int): ProductCreateData

    @PUT("produtos/{idProduto}")
    suspend fun put(@Path("idProduto") idProduto:Int, @Body produto: ProductEditData): ProductEditData
}