package com.example.gestok.domain.model.dashboard

data class OrderStatus(
    val pendente: Float,
    val emProducao: Float,
    val concluido: Float,
    val cancelado: Float
)
