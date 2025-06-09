package com.example.gestok.domain.model.product

data class SearchResponse(
    val results: List<SearchResult>
)

data class SearchResult(
    val id: Int,
    val name: String
)

data class NutritionResponse(
    val nutrition: NutritionModel
)

data class NutritionModel(
    val nutrients: List<NutrienteDto>
)

data class NutrienteDto(
    val name: String,
    val amount: Double,
    val unit: String
)