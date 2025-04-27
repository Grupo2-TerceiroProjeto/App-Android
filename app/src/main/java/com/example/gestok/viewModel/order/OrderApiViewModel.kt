package com.example.gestok.viewModel.order

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.OrderService
import com.example.gestok.components.orderpage.OrderData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrderApiViewModel(private val api: OrderService) : OrderViewModel() {

    override fun getPedidos() {
        limparErros()

        viewModelScope.launch {
            try {

                delay(2000)

                val resposta = api.getPedidos()

                _pedidos.clear()
                if (resposta.isNotEmpty()) {
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
                _carregouPedidos = true
                Log.d("API", "Quantidade de pedidos encontrados: ${pedidos.size}")

            } catch (e: HttpException) {

                if (e.code() == 500) _carregouPedidos = true

                Log.w("API", "Nem um pedido encontrado: ${e.message}  ${_carregouPedidos}")

            } catch (e: Exception) {
                _pedidosErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }

    }
}