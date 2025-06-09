package com.example.gestok.data.repository

import com.example.gestok.domain.model.product.TraducaoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LingvaRepository {

    @GET("pt/en/{texto}")
    suspend fun traduzirPtParaEn(@Path("texto") texto: String): TraducaoResponse

    @GET("en/pt/{texto}")
    suspend fun traduzirEnParaPt(@Path("texto") texto: String): TraducaoResponse
}