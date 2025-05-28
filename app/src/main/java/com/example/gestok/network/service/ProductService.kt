package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.product.data.CategoryData
import com.example.gestok.screens.internalScreens.product.data.IngredientsData
import com.example.gestok.screens.internalScreens.product.data.ProductData
import retrofit2.http.DELETE
import retrofit2.http.GET
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
}