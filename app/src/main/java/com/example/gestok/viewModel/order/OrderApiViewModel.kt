package com.example.gestok.viewModel.order

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.OrderService
import com.example.gestok.screens.internalScreens.order.data.OrderCreateData
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.ProductData
import com.example.gestok.screens.login.data.UserSession
import com.example.gestok.utils.formatDateApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrderApiViewModel(private val api: OrderService, override val sessaoUsuario: UserSession) :
    OrderViewModel(sessaoUsuario) {

    override fun getPedidos() {
        limparErros()

        viewModelScope.launch {
            try {
                delay(1100)

                val resposta = api.getPedidos()

                _pedidos.clear()
                if (resposta.isNotEmpty()) {
                    val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
                    val formatterOutput = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())

                    _pedidos.addAll(resposta.map {
                        val dataEntregaFormatada = try {
                            LocalDate.parse(it.dataEntrega, formatterInput)
                                .format(formatterOutput)
                        } catch (e: Exception) {
                            it.dataEntrega
                        }

                        OrderData(
                            id = it.id,
                            nomeSolicitante = it.nomeSolicitante,
                            dataEntrega = dataEntregaFormatada,
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

                Log.w("API", "Nenhum pedido encontrado: ${e.message}  ${_carregouPedidos}")

            } catch (e: Exception) {
                _pedidosErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }
    }

    override fun getProdutos() {
        limparErrosProdutos()

        viewModelScope.launch {
            try {

                delay(1000)

                val resposta = api.getProdutos(sessaoUsuario.idEmpresa)

                _produtos.clear()
                if (resposta.isNotEmpty()) {
                    _produtos.addAll(resposta.map {
                        ProductData(
                            id_produto = it.id_produto,
                            fk_empresa = it.fk_empresa,
                            fk_categoria = it.fk_categoria,
                            nome = it.nome,
                            preco = it.preco,
                            qtd_estoque = it.qtd_estoque,
                            em_producao = it.em_producao,
                            imagem = it.imagem
                        )
                    })
                }
                _carregouProdutos = true
                Log.d("API", "Quantidade de produtos encontrados: ${produtos.size}")

            } catch (e: HttpException) {

                if (e.code() == 500) _carregouProdutos = true

                Log.w("API", "Nem um produto encontrado: ${e.message}  ${_carregouPedidos}")

            } catch (e: Exception) {
                _produtosErro = "Erro ao obter dados"
                Log.e("API", "Erro ao obter dados: ${e.message}")
            }
        }

    }

    override fun salvarPedido(pedido: OrderCreateData, onBack: () -> Unit, onSucess: () -> Unit) {
        limparErrosPedido()

        val cadastrado =  mutableStateOf(false)

        var houveErro = false

        if (pedido.nomeSolicitante.isBlank()) {
            _nomeSolicitanteErro = "Nome do solicitante é obrigatório"
            houveErro = true
        } else if (pedido.nomeSolicitante.length < 2) {
            _nomeSolicitanteErro = "Nome do solicitante deve ter pelo menos 2 caracteres"
            houveErro = true
        }

        if (pedido.telefone.isBlank()) {
            _telefoneErro = "Número de telefone é obrigatório"
            houveErro = true
        } else if (pedido.telefone.length < 11) {
            _telefoneErro = "Número de telefone inválido"
            houveErro = true
        }

        if (pedido.status == "Selecione uma opção") {
            _statusErro = "Status do pedido é obrigatório"
            houveErro = true
        }

        if(pedido.dataEntrega.isBlank()) {
            _dataEntregaErro = "Data de entrega é obrigatória"
            houveErro = true

        } else {
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formato.isLenient = false

            try {
                formato.parse(pedido.dataEntrega)
            } catch (e: Exception) {
                _dataEntregaErro = "Data de entrega inválida"
                houveErro = true
            }
        }

        if (pedido.produtos.any { it.quantidade == 0 }) {
            _itensErro = "Informe a quantidade dos produtos selecionados"
            houveErro = true
        }

        if (houveErro) return

        viewModelScope.launch {
            try {
                cadastrado.value = false

                val dataConvertida = formatDateApi(pedido.dataEntrega)
                val pedidoFormatado = pedido.copy(dataEntrega = dataConvertida)

                api.post(pedidoFormatado)

                cadastrado.value = true
                Log.d("API", "Pedido cadastrado com sucesso")

                onSucess()

                delay(1500)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 400) cadastrado.value = false
                Log.d("API", "Erro ao cadastrar pedido: ${e.message}")

            } catch (e: Exception) {
                Log.d("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }
}