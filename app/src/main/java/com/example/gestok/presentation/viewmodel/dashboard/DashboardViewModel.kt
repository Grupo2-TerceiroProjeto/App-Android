package com.example.gestok.presentation.viewmodel.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.domain.model.dashboard.OrderStatus
import com.example.gestok.domain.model.auth.UserSession

abstract class DashboardViewModel(open val sessaoUsuario : UserSession) : ViewModel() {

    protected var _dashboardErro by mutableStateOf<String?>(null)

    protected var _pedidos = mutableStateListOf<OrderModel>()

    protected var _carregouPedidos by mutableStateOf(false)

    protected var _mediaAvaliacao by mutableDoubleStateOf(0.0)

    val dashboardErro: String?
        get() = _dashboardErro

    val pedidos: List<OrderModel>
        get() = _pedidos.toList()

    val carregouPedidos: Boolean
        get() = _carregouPedidos

    val mediaAvaliacao: Double
        get() = _mediaAvaliacao

    open fun getPedidos() {}

    abstract fun getBuscarPedidosProximos7Dias(): List<OrderModel>

    abstract fun getMediaAvaliacao()

    abstract fun getValorMedioPedidos(): Double

    abstract fun getFaturamentoMesAtual(): Double

    abstract fun getFaturamentoMesAnterior(): Double

    abstract fun getPedidosPorCategoria(): OrderStatus

    abstract fun getFaturamentoUltimos6Meses(): Pair<List<Float>, List<String>>

    abstract fun getPedidosPorMes(): Pair<List<Int>, List<String>>

}