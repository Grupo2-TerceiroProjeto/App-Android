package com.example.gestok.components.productpage

data class ProductData(
    val produto: String,
    val estoque: Int,
    val categoria: String,
    val valor: Double,
    val ingredientes: List<IngredientData>,
    var disponbilidade: Boolean
)

