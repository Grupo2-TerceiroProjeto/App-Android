package com.example.gestok.components.orderpage

data class OrderData(
    val nomeSolicitante: String,
    val contato: String,
    val statusPedido: String,
    val dataEntrega: String,
    val itens: List<String>
)

