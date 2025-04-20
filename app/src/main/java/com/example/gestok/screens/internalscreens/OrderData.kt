package com.example.gestok.screens.internalScreens

data class Produto(
    val id: Int,
    val nome: String,
    val preco: Double,
    val quantidade: Int
)

data class OrderData(
    val id: Int,
    val nomeSolicitante: String,
    val dataEntrega: String,
    val telefone: String,
    val status: String,
    val produtos: List<Produto>,
    val totalCompra: Double
)

