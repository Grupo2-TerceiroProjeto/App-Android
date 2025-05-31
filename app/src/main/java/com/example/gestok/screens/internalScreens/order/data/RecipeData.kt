package com.example.gestok.screens.internalScreens.order.data

class RecipeData (
    val idReceita: Int,
    val quantidade: Double,
    val ingrediente: Int,
    val produto: Int
)

class RecipeBody (
    val quantidade: Double,
    val idIngrediente: Int,
    val idProduto: Int
)