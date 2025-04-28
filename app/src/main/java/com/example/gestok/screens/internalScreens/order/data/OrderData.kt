package com.example.gestok.screens.internalScreens.order.data

data class OrderItens(
    val id: Int? = 0,
    val nome: String,
    val categoria: Int? = 0,
    val preco: Double? = 0.0,
    var quantidade: Int,
    val imagem: String? = "",
    val emProducao: Boolean? = false

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

