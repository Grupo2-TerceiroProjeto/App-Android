package com.example.gestok.viewModel.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.components.orderpage.OrderData
import com.example.gestok.screens.internalScreens.dashboard.data.OrderStatus
import com.example.gestok.screens.login.data.UserSession

abstract class DashboardViewModel(open val sessaoUsuario : UserSession) : ViewModel() {

    protected var _dashboardErro by mutableStateOf<String?>(null)

    protected var _pedidos = mutableStateListOf<OrderData>()

    protected var _carregouPedidos by mutableStateOf(false)

    protected var _mediaAvaliacao by mutableDoubleStateOf(0.0)

    val dashboardErro: String?
        get() = _dashboardErro

    val pedidos: List<OrderData>
        get() = _pedidos.toList()

    val carregouPedidos: Boolean
        get() = _carregouPedidos

    val mediaAvaliacao: Double
        get() = _mediaAvaliacao

    fun limparErros() {
        _dashboardErro = null
    }

    open fun getPedidos() {}

    abstract fun getBuscarPedidosProximos7Dias(): List<OrderData>

    abstract fun getMediaAvaliacao()

    abstract fun getValorMedioPedidos(): Double

    abstract fun getFaturamentoMesAtual(): Double

    abstract fun getFaturamentoMesAnterior(): Double

    abstract fun getPedidosPorCategoria(): OrderStatus

    abstract fun getFaturamentoUltimos6Meses(): Pair<List<Float>, List<String>>

    abstract fun getPedidosPorMes(): Pair<List<Int>, List<String>>

}