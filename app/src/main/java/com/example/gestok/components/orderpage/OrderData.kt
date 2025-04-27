package com.example.gestok.components.orderpage

import com.example.gestok.components.productpage.ProductData

data class OrderData(
    val nomeSolicitante: String,
    val contato: String,
    val statusPedido: String,
    val dataEntrega: String,
    val itens: MutableMap<ProductData, Int>
)

