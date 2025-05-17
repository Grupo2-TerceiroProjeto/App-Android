package com.example.gestok.screens.internalScreens.order.data

data class OrderItensBlock(
    val nome: String,
    var quantidade: Int
)

data class OrderItens(
    val id: Int,
    val nome: String,
    val categoria: Int,
    val preco: Double,
    var quantidade: Int,
    val imagem: String,
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

data class OrderItensCreate(
    val nome: String,
    val categoria: Int,
    val preco: Double,
    var quantidade: Int,
    val imagem: String,
    val emProducao: Boolean

)

data class OrderCreateData(
    val nomeSolicitante: String,
    val dataEntrega: String,
    val telefone: String,
    val status: String,
    val produtos: List<OrderItensCreate>
)

data class OrderEditData(
    val nomeSolicitante: String,
    val dataEntrega: String,
    val telefone: String,
    val status: String,
    val produtos: List<OrderItensBlock>
)

