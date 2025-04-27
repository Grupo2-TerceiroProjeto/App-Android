package com.example.gestok.viewModel.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.components.orderpage.OrderData

abstract class OrderViewModel : ViewModel() {

    protected var _pedidosErro by mutableStateOf<String?>(null)

    protected var _pedidos = mutableListOf<OrderData>()

    protected var _carregouPedidos by mutableStateOf(false)

    val pedidosErro: String?
        get() = _pedidosErro


    val pedidos: List<OrderData>
        get() = _pedidos.toList()

    val carregouPedidos: Boolean
        get() = _carregouPedidos

    fun limparErros() {
        _pedidosErro = null
    }

    open fun getPedidos() {}
}