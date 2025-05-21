package com.example.gestok.screens.internalScreens.order.data

data class ProductData(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Double,
    val imagem: String?,
    val emProducao: Boolean
)