package com.example.gestok.viewModel.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.internalScreens.dashboard.OrderData

abstract class DashboardViewModel : ViewModel() {

    protected var _dashboardErro by mutableStateOf<String?>(null)

    protected var _pedidos = mutableStateListOf<OrderData>()

    protected var _mediaAvaliacao by mutableDoubleStateOf(0.0)

    protected var _carregouPedidos by mutableStateOf(false)

    val dashboardErro: String?
        get() = _dashboardErro

    val pedidos: List<OrderData>
        get() = _pedidos.toList()

    val mediaAvaliacao: Double
        get() = _mediaAvaliacao

    val carregouPedidos: Boolean
        get() = _carregouPedidos

    fun limparErros() {
        _dashboardErro = null
    }

    open fun buscarTodos() {}

    abstract fun buscarPedidosProximos7Dias(): List<OrderData>

}