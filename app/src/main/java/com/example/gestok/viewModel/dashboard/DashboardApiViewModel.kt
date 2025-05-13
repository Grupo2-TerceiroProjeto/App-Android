package com.example.gestok.viewModel.dashboard

import androidx.lifecycle.viewModelScope
import com.example.gestok.screens.internalScreens.order.data.OrderData
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.gestok.network.service.DashboardService
import com.example.gestok.screens.internalScreens.dashboard.data.AssessmentData
import com.example.gestok.screens.internalScreens.dashboard.data.OrderStatus
import com.example.gestok.screens.login.data.UserSession
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.format.TextStyle
import java.util.Locale

class DashboardApiViewModel(private val api: DashboardService, override val sessaoUsuario : UserSession) : DashboardViewModel(sessaoUsuario) {

    override fun getPedidos() {
        limparErros()

        viewModelScope.launch {
            try {
                delay(1100)
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
                    Log.w("API", "Nenhuma avaliação encontrada")
                    _mediaAvaliacao = 0.0
                }

                Log.d("API", "Quantidade de avaliações encontradas: ${avaliacoes.size}")

            } catch (e: Exception) {
                _dashboardErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }
    }

    override fun getValorMedioPedidos(): Double {
        val totalPedidos = pedidos.size
        val totalValor = pedidos.sumOf { it.totalCompra ?: 0.0 }

        return if (totalPedidos > 0) {
            val media = totalValor / totalPedidos
            val resultado = BigDecimal(media).setScale(2, RoundingMode.HALF_UP).toDouble()
            Log.d("API", "Valor médio calculado: $resultado")
            resultado
        } else {
            Log.w("API", "Nenhum pedido encontrado para calcular média")
            0.0
        }
    }

    override fun getFaturamentoMesAtual(): Double {
        val hoje = LocalDate.now()
        val primeiroDiaMes = hoje.withDayOfMonth(1)
        val ultimoDiaMes = hoje.withDayOfMonth(hoje.lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val pedidosFiltrados = pedidos.filter { pedido ->
            val dataEntrega = pedido.dataEntrega?.let {
                try {
                    LocalDate.parse(it, formatter)
                } catch (e: Exception) {
                    Log.e("API", "Erro ao converter data de entrega: ${pedido.dataEntrega}", e)
                    null
                }
            }

            pedido.status == "Concluído" &&
                    dataEntrega != null &&
                    dataEntrega >= primeiroDiaMes &&
                    dataEntrega <= ultimoDiaMes
        }

        val total = pedidosFiltrados.sumOf { it.totalCompra ?: 0.0 }
        Log.d("API", "Faturamento mês atual: $total")

        return BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).toDouble()

    }

    override fun getFaturamentoMesAnterior(): Double {
        val hoje = LocalDate.now()
        val primeiroDiaMesAnterior = hoje.minusMonths(1).withDayOfMonth(1)
        val ultimoDiaMesAnterior = hoje.minusMonths(1).withDayOfMonth(hoje.minusMonths(1).lengthOfMonth())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val pedidosFiltrados = pedidos.filter { pedido ->
            val dataEntrega = pedido.dataEntrega?.let {
                try {
                    LocalDate.parse(it, formatter)
                } catch (e: Exception) {
                    Log.e("API", "Erro ao converter data de entrega: ${pedido.dataEntrega}", e)
                    null
                }
            }

            pedido.status == "Concluído" &&
                    dataEntrega != null &&
                    dataEntrega >= primeiroDiaMesAnterior &&
                    dataEntrega <= ultimoDiaMesAnterior
        }

        val total = pedidosFiltrados.sumOf { it.totalCompra ?: 0.0 }
        Log.d("API", "Faturamento mês anterior: $total")

        return BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).toDouble()

    }

    override fun getPedidosPorCategoria(): OrderStatus {
        val pendente = pedidos.count { it.status == "Pendente" }.toFloat()
        val emProducao = pedidos.count { it.status == "Em Produção" }.toFloat()
        val concluido = pedidos.count { it.status == "Concluído" }.toFloat()
        val cancelado = pedidos.count { it.status == "Cancelado" }.toFloat()

        Log.d(
            "API",
            "Pedidos por categoria: " +
                    "Pendente: ${pendente.toInt()}, " +
                    "Em Produção: ${emProducao.toInt()}, " +
                    "Concluído: ${concluido.toInt()}, " +
                    "Cancelado: ${cancelado.toInt()}"
        )

        return OrderStatus(
            pendente = pendente,
            emProducao = emProducao,
            concluido = concluido,
            cancelado = cancelado
        )

    }

    override fun getFaturamentoUltimos6Meses(): Pair<List<Float>, List<String>> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val hoje = LocalDate.now()

        val meses = (0..5).map { hoje.minusMonths(it.toLong()).withDayOfMonth(1) }.reversed()

        val labels = meses.map {
            val nomeMes = it.month.getDisplayName(TextStyle.SHORT, Locale("pt", "BR"))
            "${nomeMes.replaceFirstChar { c -> c.uppercase() }} ${it.year}"
        }

        val faturamentos = meses.map { mes ->
            val inicio = mes
            val fim = mes.withDayOfMonth(mes.lengthOfMonth())

            val total = pedidos.filter { pedido ->
                val dataEntrega = pedido.dataEntrega?.let {
                    try {
                        LocalDate.parse(it, formatter)
                    } catch (e: Exception) {
                        Log.d("API", "Erro ao converter data de entrega: ${pedido.dataEntrega}", e)
                        null
                    }
                }

                pedido.status == "Concluído" &&
                        dataEntrega != null &&
                        dataEntrega >= inicio &&
                        dataEntrega <= fim

            }.sumOf { it.totalCompra ?: 0.0 }

            BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN).toFloat()
        }

        return Pair(faturamentos, labels)

    }

    override fun getPedidosPorMes(): Pair<List<Int>, List<String>> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val hoje = LocalDate.now()

        val meses = (0..5).map { hoje.minusMonths(it.toLong()).withDayOfMonth(1) }.reversed()

        val labels = meses.map {
            val nomeMes = it.month.getDisplayName(TextStyle.SHORT, Locale("pt", "BR"))
            "${nomeMes.replaceFirstChar { c -> c.uppercase() }} ${it.year}"
        }

        val quantidades = meses.map { mes ->
            val inicio = mes
            val fim = mes.withDayOfMonth(mes.lengthOfMonth())

            pedidos.count { pedido ->
                val dataEntrega = pedido.dataEntrega?.let {
                    try {
                        LocalDate.parse(it, formatter)
                    } catch (e: Exception) {
                        Log.d("API", "Erro ao converter data de entrega: ${pedido.dataEntrega}", e)
                        null
                    }
                }

                pedido.status == "Concluído" &&
                        dataEntrega != null &&
                        dataEntrega >= inicio &&
                        dataEntrega <= fim
            }
        }

        return Pair(quantidades, labels)
    }

}