package com.example.gestok.network.service

import com.example.gestok.screens.internalScreens.product.data.TraducaoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LingvaService {

    @GET("pt/en/{texto}")
    suspend fun traduzirPtParaEn(@Path("texto") texto: String): TraducaoResponse

    @GET("en/pt/{texto}")
    suspend fun traduzirEnParaPt(@Path("texto") texto: String): TraducaoResponse
}