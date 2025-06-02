package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.order.data.RecipeBody
import com.example.gestok.screens.internalScreens.order.data.RecipeData
import com.example.gestok.screens.internalScreens.product.data.CategoryData
import com.example.gestok.screens.internalScreens.product.data.IngredientsBody
import com.example.gestok.screens.internalScreens.product.data.IngredientsData
import com.example.gestok.screens.internalScreens.product.data.ProductCreateData
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.screens.internalScreens.product.data.ProductEditData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {

    @GET("produtos/pegar-todos/{idEmpresa}")
    suspend fun getProdutos(@Path("idEmpresa") idEmpresa:Int): List<ProductData>

    @DELETE("produtos/{idProduto}")
    suspend fun delete(@Path("idProduto") idProduto:Int)

    @POST("produtos/{empresaId}")
    suspend fun post(@Body produto: ProductCreateData, @Path("empresaId") empresaId:Int): ProductCreateData

    @PUT("produtos/{idProduto}")
    suspend fun put(@Path("idProduto") idProduto:Int, @Body produto: ProductEditData): ProductEditData

    @GET("ingredientes/all")
    suspend fun getIngredientes(): List<IngredientsData>

    @POST("ingredientes/{idProduto}")
    suspend fun postIngrediente(@Path("idProduto") idProduto:Int, @Body ingrediente: IngredientsBody): IngredientsBody

    @PUT("ingredientes/{idIngrediente}")
    suspend fun putIngrediente(@Body ingrediente: IngredientsBody, @Path("idIngrediente") idIngrediente:Int): IngredientsBody

    @DELETE("ingredientes/{idIngrediente}")
    suspend fun deleteIngrediente(@Path("idIngrediente") id:Int)

    @GET("receitas/listar")
    suspend fun getReceitas(): List<RecipeData>

    @POST("receitas")
    suspend fun postReceita(@Body receita: RecipeBody): RecipeBody

    @PUT("receitas/{id}")
    suspend fun putReceita( @Path("id") id:Int, @Body receita: RecipeBody): RecipeBody

    @DELETE("receitas/{id}")
    suspend fun deleteReceita(@Path("id") id:Int)

    @GET("nutrientes/pegar-todos/{idIngrediente}")
    suspend fun getNutrientes(@Path("idIngrediente") idIngrediente: Int): Response<ResponseBody>

    @GET("categorias/all")
    suspend fun getCategorias(): List<CategoryData>

}