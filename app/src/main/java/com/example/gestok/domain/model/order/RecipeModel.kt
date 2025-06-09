package com.example.gestok.domain.model.order

class RecipeModel (
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