package com.example.gestok.viewModel.dashboard

import androidx.lifecycle.viewModelScope
import com.example.gestok.screens.internalScreens.dashboard.OrderData
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.gestok.network.service.DashboardService
import com.example.gestok.screens.internalScreens.dashboard.AssessmentData
import com.example.gestok.screens.login.UserSession
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import retrofit2.HttpException

class DashboardApiViewModel(private val api: DashboardService, override val sessaoUsuario : UserSession) : DashboardViewModel(sessaoUsuario) {

    override fun getBuscarTodos() {
        limparErros()

        viewModelScope.launch {
            try {
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

                Log.e("API", "Nem um pedido encontrado: ${e.message}")

            } catch (e: Exception) {
                _dashboardErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }
    }

    override fun getBuscarPedidosProximos7Dias(): List<OrderData> {
        val hoje = LocalDate.now()
        val seteDiasDepois = hoje.plusDays(7)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val pedidosFiltrados = pedidos.filter { pedido ->
            val dataEntrega = pedido.dataEntrega?.let {
                try {
                    LocalDate.parse(it, formatter)
                } catch (e: Exception) {
                    null
                }
            }

            pedido.status == "Em Produção" &&
                    dataEntrega != null &&
                    dataEntrega >= hoje &&
                    dataEntrega <= seteDiasDepois
        }

        Log.d("API", "Quantidade de pedidos encontrados para os próximos 7 dias: ${pedidosFiltrados.size}")

        return pedidosFiltrados
    }

    override fun getMediaAvaliacao() {
        limparErros()

        var avaliacoes = mutableStateListOf<AssessmentData>()

        viewModelScope.launch {
            try {
                val resposta = api.getAvaliacoes(sessaoUsuario.idEmpresa)

                avaliacoes.clear()

                val corpo = resposta.body()?.string()

                if (!corpo.isNullOrBlank() && corpo != "[]") {
                    val gson = Gson()
                    val lista: List<AssessmentData> = gson.fromJson(
                        corpo,
                        object : TypeToken<List<AssessmentData>>() {}.type
                    )

                    avaliacoes.addAll(lista)

                    _mediaAvaliacao = lista.map { it.nota }.average()
                    Log.d("API", "Média: ${_mediaAvaliacao}")

                } else {

                    _mediaAvaliacao = 0.0
                }

                Log.d("API", "Quantidade de avaliações encontradas: ${avaliacoes.size}")

            } catch (e: Exception) {
                _dashboardErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }
    }

}