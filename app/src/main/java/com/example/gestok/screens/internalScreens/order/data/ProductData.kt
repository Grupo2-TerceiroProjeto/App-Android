package com.example.gestok.screens.internalScreens.order.data

data class ProductData(
    val id_produto: Int,
    val fk_empresa: Int,
    val fk_categoria: Int,
    val nome: String,
    val preco: Double,
    val qtd_estoque: Int,
    val em_producao: Boolean,
    val imagem: String?
)