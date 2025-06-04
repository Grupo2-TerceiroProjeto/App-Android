package com.example.gestok.screens.internalScreens.product.data

data class NutrientesResponse(
    val id: Int,
    val tipo: String,
    val pcComposicao: String
)

data class NutrientesBody(
    val tipo: String,
    val pcComposicao: String
)

data class Nutriente(
    val name: String,
    val amount: Double,
    val unit: String
)
