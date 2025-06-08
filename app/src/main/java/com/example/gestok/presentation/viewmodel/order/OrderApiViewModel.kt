package com.example.gestok.presentation.viewmodel.order

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.gestok.data.repository.OrderRepository
import com.example.gestok.domain.model.order.OrderCreateModel
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.domain.model.order.OrderEditModel
import com.example.gestok.domain.model.order.OrderItens
import com.example.gestok.domain.model.order.OrderItensBlock
import com.example.gestok.domain.model.order.RecipeModel
import com.example.gestok.domain.model.product.IngredientsModel
import com.example.gestok.domain.model.product.IngredientsFormat
import com.example.gestok.domain.model.product.ProductModel
import com.example.gestok.domain.model.auth.UserSession
import com.example.gestok.utils.formatDateApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrderApiViewModel(private val api: OrderRepository, override val sessaoUsuario: UserSession) :
    OrderViewModel(sessaoUsuario) {

    override fun getPedidos() {
        _pedidosErro = null

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

                        OrderModel(
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
        _produtosErro = null

        viewModelScope.launch {
            try {

                delay(1000)

                val resposta = api.getProdutos(sessaoUsuario.idEmpresa)

                _produtos.clear()
                if (resposta.isNotEmpty()) {
                    _produtos.addAll(resposta.map {
                        ProductModel(
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

    override fun salvarPedido(pedido: OrderCreateModel, onBack: () -> Unit, onSucess: () -> Unit) {
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
                Log.e("API", "Erro ao cadastrar pedido: ${e.message}")
                _cadastroErro = "Erro ao cadastrar pedido"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _cadastroErro = "Erro ao conectar ao servidor"
            }
        }
    }

    override fun editarPedido(pedido: OrderEditModel, excluidosFiltrados: List<OrderItens>, idPedido: Int, onBack: () -> Unit, onSucess: () -> Unit) {
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
                val todosProdutos = pedido.produtos + excluidosFiltrados.map {
                    OrderItensBlock(nome = it.nome, quantidade = 0)
                }

                val dataConvertida = formatDateApi(pedido.dataEntrega)

                val pedidoFormatado = pedido.copy(
                    dataEntrega = dataConvertida,
                    produtos = todosProdutos
                )


                api.put(pedidoFormatado, idPedido)

                Log.d("API", "Pedido editado com sucesso")

                onSucess()
                getPedidos()

                delay(1500)
                onBack()

            } catch (e: HttpException) {
                if (e.code() == 400 || e.code() == 401) {}
                Log.e("API", "Erro ao editar pedido: ${e.message}")
                _edicaoErro = "Erro ao editar pedido"

            } catch (e: Exception) {
                Log.e("API", "Erro ao conectar ao servidor: ${e.message}")
                _edicaoErro = "Erro ao conectar ao servidor"
            }
        }
    }

    override fun getReceita(pedido: OrderModel, onResult: (List<IngredientsFormat>) -> Unit) {

        viewModelScope.launch {
            try {

                val produtos : List<ProductModel> = api.getProdutos(sessaoUsuario.idEmpresa)
                val receitas : List<RecipeModel> = api.getReceitas()
                val ingredientes : List<IngredientsModel> = api.getIngredientes()

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
                    4 to "un"
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

                val ingredientesUnificados = ingredientesFiltrados
                    .groupBy { it.id }
                    .map { (_, itens) ->
                        val primeiro = itens.first()
                        primeiro.copy(
                            quantidade = itens.sumOf { it.quantidade }
                        )
                    }

                Log.d("API", "Receita obtida com sucesso")

                onResult(ingredientesUnificados)

            } catch (e: Exception) {
                Log.e("API", "Erro ao obter receita: ${e.message}")
                onResult(emptyList())
            }
        }
    }
}