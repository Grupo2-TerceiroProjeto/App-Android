package com.example.gestok.screens.internalScreens.product.data

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

data class IngredientsProduct (
    val id: Int,
    val nome: String,
    val quantidade: Double
)

data class IngredientsBlock (
    val nome: String,
    val quantidade: Int
)

data class IngredientsRecipe (
    val id: Int,
    val idIngrediente: Int,
    val nome: String,
    val quantidade: Double
)

data class IngredientsCreate (
    val nome: String,
    val quantidade: Int,
    val medida: Double,
)


