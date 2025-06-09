package com.example.gestok.domain.model.order

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

data class OrderModel(
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

data class OrderCreateModel(
    val nomeSolicitante: String,
    val dataEntrega: String,
    val telefone: String,
    val status: String,
    val produtos: List<OrderItensCreate>
)

data class OrderEditModel(
    val nomeSolicitante: String,
    val dataEntrega: String,
    val telefone: String,
    val status: String,
    val produtos: List<OrderItensBlock>
)