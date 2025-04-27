package com.example.gestok.components.orderpage

data class OrderItens(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    var quantidade: Int,
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

