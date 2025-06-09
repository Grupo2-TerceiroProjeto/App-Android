package com.example.gestok.data.repository

import com.example.gestok.domain.model.product.NutritionResponse
import com.example.gestok.domain.model.product.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularRepository {

    @GET("search")
    suspend fun searchIngrediente(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String
    ): SearchResponse

    @GET("{id}/information")
    suspend fun getInformacaoIngrediente(
        @Path("id") id: Int,
        @Query("amount") amount: String,
        @Query("unit") unit: String,
        @Query("apiKey") apiKey: String
    ): NutritionResponse
}
