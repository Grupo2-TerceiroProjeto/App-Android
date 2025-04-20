package com.example.gestok.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.ApiClient
import com.example.gestok.screens.internalScreens.OrderData
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardViewModel : ViewModel() {

    private var _dashboardErro by mutableStateOf<String?>(null)

    private var _pedidos = mutableStateListOf<OrderData>()

    val dashboardErro: String?
        get() = _dashboardErro

    val pedidos: List<OrderData>
        get() = _pedidos.toList()

    private fun limparErros() {
        _dashboardErro = null
    }

    fun buscarTodos() {
        limparErros()

        viewModelScope.launch {
            try {
                val resposta = ApiClient.dashboardService.getPedidos()

                if (resposta.isEmpty()) {
                    _pedidos
                } else {
                    _pedidos.addAll(resposta.map {
                        OrderData(
                            id = it.id,
                            nomeSolicitante = it.nomeSolicitante,
                            dataEntrega = it.dataEntrega,
                            telefone = it.telefone,
                            status = it.status,
                            produtos = it.produtos,
                            totalCompra = it.totalCompra
                        )
                    })
                }

            } catch (e: Exception) {
                _dashboardErro = "Erro ao obter dados"
            }
        }
    }

    fun buscarPedidosProximos7Dias(): List<OrderData> {
        val hoje = LocalDate.now()
        val seteDiasDepois = hoje.plusDays(7)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return pedidos.filter { pedido ->
            val dataEntrega = pedido.dataEntrega?.let {
                try {
                    LocalDate.parse(it, formatter)
                } catch (e: Exception) {
                    null
                }
            }

            pedido.status == "Em Produção" && dataEntrega != null && dataEntrega >= hoje && dataEntrega <= seteDiasDepois
        }
    }

}