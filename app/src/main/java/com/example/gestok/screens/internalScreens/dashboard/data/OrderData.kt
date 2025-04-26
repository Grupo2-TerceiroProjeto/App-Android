package com.example.gestok.screens.internalScreens.dashboard.data

data class OrderItens(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    val quantidade: Int,
    val imagem: String?,
    val emProducao: Boolean

)

data class OrderData(
    val id: Int,
    val nomeSolicitante: String,
    val dataEntrega: String?,
    val telefone: String,
    val status: String,
    val produtos: List<OrderItens>,
    val totalCompra: Double
)

