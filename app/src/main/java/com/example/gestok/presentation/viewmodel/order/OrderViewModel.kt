package com.example.gestok.presentation.viewmodel.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.domain.model.order.OrderCreateModel
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.domain.model.order.OrderEditModel
import com.example.gestok.domain.model.order.OrderItens
import com.example.gestok.domain.model.product.IngredientsFormat
import com.example.gestok.domain.model.product.ProductModel
import com.example.gestok.domain.model.auth.UserSession

abstract class OrderViewModel(open val sessaoUsuario : UserSession) : ViewModel() {

    protected var _pedidosErro by mutableStateOf<String?>(null)

    protected var _produtosErro by mutableStateOf<String?>(null)

    protected var _pedidos = mutableListOf<OrderModel>()

    protected var _produtos = mutableListOf<ProductModel>()

    protected var _carregouPedidos by mutableStateOf(false)

    protected var _carregouProdutos by mutableStateOf(false)

    protected var _nomeSolicitanteErro by mutableStateOf<String?>(null)

    protected var _telefoneErro by mutableStateOf<String?>(null)

    protected var _statusErro by mutableStateOf<String?>(null)

    protected var _dataEntregaErro by mutableStateOf<String?>(null)

    protected var _itensErro by mutableStateOf<String?>(null)

    protected var _cadastroErro by mutableStateOf<String?>(null)

    protected var _edicaoErro by mutableStateOf<String?>(null)

    val pedidosErro: String?
        get() = _pedidosErro

    val produtosErro: String?
        get() = _produtosErro

    val pedidos: List<OrderModel>
        get() = _pedidos.toList()

    val produtos: List<ProductModel>
        get() = _produtos.toList()

    val carregouPedidos: Boolean
        get() = _carregouPedidos

    val carregouProdutos: Boolean
        get() = _carregouProdutos

    val nomeSolicitanteErro: String?
        get() = _nomeSolicitanteErro

    val telefoneErro: String?
        get() = _telefoneErro

    val statusErro: String?
        get() = _statusErro

    val dataEntregaErro : String?
        get() = _dataEntregaErro

    val itensErro : String?
        get() = _itensErro

    val cadastroErro : String?
        get() = _cadastroErro

    val edicaoErro : String?
        get() = _edicaoErro

    fun limparErrosFormulario() {
        _nomeSolicitanteErro = null
        _telefoneErro = null
        _statusErro = null
        _dataEntregaErro = null
        _itensErro = null
        _cadastroErro = null
        _edicaoErro = null
    }

    open fun getPedidos() {}

    open fun getProdutos() {}

    open fun salvarPedido(pedido : OrderCreateModel, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun editarPedido(pedido : OrderEditModel, excluidosFiltrados: List<OrderItens>, idPedido: Int, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun getReceita(pedido: OrderModel, onResult: (List<IngredientsFormat>) -> Unit) {}
}