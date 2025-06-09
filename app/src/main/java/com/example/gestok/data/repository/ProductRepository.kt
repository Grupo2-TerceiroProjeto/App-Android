package com.example.gestok.data.repository

import com.example.gestok.domain.model.order.RecipeBody
import com.example.gestok.domain.model.order.RecipeModel
import com.example.gestok.domain.model.product.CategoryModel
import com.example.gestok.domain.model.product.IngredientsBody
import com.example.gestok.domain.model.product.IngredientsModel
import com.example.gestok.domain.model.product.NutrientesBody
import com.example.gestok.domain.model.product.NutrientesResponse
import com.example.gestok.domain.model.product.ProductCreateModel
import com.example.gestok.domain.model.product.ProductModel
import com.example.gestok.domain.model.product.ProductEditModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductRepository {

    @GET("produtos/pegar-todos/{idEmpresa}")
    suspend fun getProdutos(@Path("idEmpresa") idEmpresa:Int): List<ProductModel>

    @DELETE("produtos/{idProduto}")
    suspend fun delete(@Path("idProduto") idProduto:Int)

    @POST("produtos/{empresaId}")
    suspend fun post(@Body produto: ProductCreateModel, @Path("empresaId") empresaId:Int): ProductCreateModel

    @PUT("produtos/{idProduto}")
    suspend fun put(@Path("idProduto") idProduto:Int, @Body produto: ProductEditModel): ProductEditModel

    @GET("ingredientes/all")
    suspend fun getIngredientes(): List<IngredientsModel>

    @POST("ingredientes/{idProduto}")
    suspend fun postIngrediente(@Path("idProduto") idProduto:Int, @Body ingrediente: IngredientsBody): IngredientsBody

    @PUT("ingredientes/{idIngrediente}")
    suspend fun putIngrediente(@Body ingrediente: IngredientsBody, @Path("idIngrediente") idIngrediente:Int): IngredientsBody

    @DELETE("ingredientes/{idIngrediente}")
    suspend fun deleteIngrediente(@Path("idIngrediente") id:Int)

    @GET("receitas/listar")
    suspend fun getReceitas(): List<RecipeModel>

    @POST("receitas")
    suspend fun postReceita(@Body receita: RecipeBody): RecipeBody

    @PUT("receitas/{id}")
    suspend fun putReceita( @Path("id") id:Int, @Body receita: RecipeBody): RecipeBody

    @DELETE("receitas/{id}")
    suspend fun deleteReceita(@Path("id") id:Int)

    @GET("nutrientes/pegar-todos/{idIngrediente}")
    suspend fun getNutrientes(@Path("idIngrediente") idIngrediente: Int): Response<ResponseBody>

    @POST("nutrientes/{idIngrediente}")
    suspend fun postNutrientes(@Body nutriente: NutrientesBody, @Path("idIngrediente") idIngrediente: Int): NutrientesResponse

    @GET("categorias/all")
    suspend fun getCategorias(): List<CategoryModel>

}