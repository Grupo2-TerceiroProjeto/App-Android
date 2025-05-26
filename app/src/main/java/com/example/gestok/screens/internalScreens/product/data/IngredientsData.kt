package com.example.gestok.screens.internalScreens.order.data

data class IngredientsData (
    val id: Int,
    val nome: String,
    val medida: Double,
    val quantidade: Double
)

data class IngredientsFormat (
    val id: Int,
    val nome: String,
    val medida: String,
    val quantidade: Int
)