package com.example.gestok.viewModel.order

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.example.gestok.network.service.OrderService
import com.example.gestok.screens.internalScreens.order.data.IngredientsData
import com.example.gestok.screens.internalScreens.order.data.IngredientsFormat
import com.example.gestok.screens.internalScreens.order.data.OrderCreateData
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.OrderEditData
import com.example.gestok.screens.internalScreens.order.data.ProductData
import com.example.gestok.screens.internalScreens.order.data.RecipeData
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
                Log.d("API", "PEDIDOS: ${pedidos}")

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
                            id = it.id,
                            nome = it.nome,
                            categoria = it.categoria,
                            preco = it.preco,
                            quantidade = it.quantidade,
                            imagem = it.imagem,
                            emProducao = it.emProducao
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
        limparErrosFormulario()

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

                val dataConvertida = formatDateApi(pedido.dataEntrega)
                val pedidoFormatado = pedido.copy(dataEntrega = dataConvertida)

                api.post(pedidoFormatado)

                Log.d("API", "Pedido cadastrado com sucesso")

                onSucess()
                getPedidos()

                delay(1500)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 400) {}
                Log.d("API", "Erro ao cadastrar pedido: ${e.message}")

            } catch (e: Exception) {
                Log.d("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun editarPedido(pedido: OrderEditData, idPedido: Int, onBack: () -> Unit, onSucess: () -> Unit) {
        limparErrosFormulario()

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

                val dataConvertida = formatDateApi(pedido.dataEntrega)
                val pedidoFormatado = pedido.copy(dataEntrega = dataConvertida)

                api.put(pedidoFormatado, idPedido)

                Log.d("API", "Pedido editado com sucesso")

                onSucess()
                getPedidos()

                delay(1500)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 400 || e.code() == 401) {}
                Log.d("API", "Erro ao editar pedido: ${e.message}")

            } catch (e: Exception) {
                Log.d("API", "Erro ao conectar ao servidor: ${e.message}")
            }
        }
    }

    override fun getReceita(pedido: OrderData, onResult: (List<IngredientsFormat>) -> Unit) {

        viewModelScope.launch {
            try {

                val produtos : List<ProductData> = api.getProdutos(sessaoUsuario.idEmpresa)
                val receitas : List<RecipeData> = api.getReceitas()
                val ingredientes : List<IngredientsData> = api.getIngredientes()

                val nomesProdutosPedido = pedido.produtos.map { it.nome }

                val produtosFiltrados = produtos.filter { nomesProdutosPedido.contains(it.nome) }

                val idProdutos = produtosFiltrados.map { it.id }

                val receitasFiltradas = receitas.filter { receita ->
                    idProdutos.contains(receita.produto)
                }

                val medidasMap = mapOf(
                    1 to "g",
                    2 to "ml",
                    3 to "kg",
                    4 to "unidade"
                )

                val ingredientesFiltrados = receitasFiltradas.mapNotNull { receita ->
                    val ingrediente = ingredientes.find { it.id == receita.ingrediente }

                    ingrediente?.let {
                        val medidaInt = it.medida.toInt()
                        val medidaString = medidasMap[medidaInt] ?: "medida desconhecida"

                        IngredientsFormat(
                            id = it.id,
                            nome = it.nome,
                            medida = medidaString,
                            quantidade = receita.quantidade.toInt()
                        )
                    }
                }

                Log.d("API", "Receita obtida com sucesso")

                onResult(ingredientesFiltrados)

            } catch (e: Exception) {
                Log.d("API", "Erro ao obter receita: ${e.message}")
                onResult(emptyList())
            }
        }
    }
}