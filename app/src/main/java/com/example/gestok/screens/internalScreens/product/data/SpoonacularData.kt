package com.example.gestok.screens.internalScreens.product.data

data class SearchResponse(
    val results: List<SearchResult>
)

data class SearchResult(
    val id: Int,
    val name: String
)

data class NutritionResponse(
    val nutrition: NutritionData
)

data class NutritionData(
    val nutrients: List<NutrienteDto>
)

data class NutrienteDto(
    val name: String,
    val amount: Double,
    val unit: String
)