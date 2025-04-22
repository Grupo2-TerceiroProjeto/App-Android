package com.example.gestok.screens.internalScreens.dashboard

data class Product(
    val id: Int,
    val nome: String,
    val preco: Double,
    val quantidade: Int
)

data class OrderData(
    val id: Int,
    val nomeSolicitante: String,
    val dataEntrega: String?,
    val telefone: String,
    val status: String,
    val produtos: List<Product>,
    val totalCompra: Double
)

