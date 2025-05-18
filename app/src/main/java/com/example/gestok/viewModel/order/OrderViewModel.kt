package com.example.gestok.viewModel.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.internalScreens.order.data.IngredientsFormat
import com.example.gestok.screens.internalScreens.order.data.OrderCreateData
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.OrderEditData
import com.example.gestok.screens.internalScreens.order.data.ProductData
import com.example.gestok.screens.login.data.UserSession

abstract class OrderViewModel(open val sessaoUsuario : UserSession) : ViewModel() {

    protected var _pedidosErro by mutableStateOf<String?>(null)

    protected var _produtosErro by mutableStateOf<String?>(null)

    protected var _pedidos = mutableListOf<OrderData>()

    protected var _produtos = mutableListOf<ProductData>()

    protected var _carregouPedidos by mutableStateOf(false)

    protected var _carregouProdutos by mutableStateOf(false)

    protected var _nomeSolicitanteErro by mutableStateOf<String?>(null)

    protected var _telefoneErro by mutableStateOf<String?>(null)

    protected var _statusErro by mutableStateOf<String?>(null)

    protected var _dataEntregaErro by mutableStateOf<String?>(null)

    protected var _itensErro by mutableStateOf<String?>(null)

    protected var _receita = mutableListOf<IngredientsFormat>()

    val pedidosErro: String?
        get() = _pedidosErro

    val produtosErro: String?
        get() = _produtosErro

    val pedidos: List<OrderData>
        get() = _pedidos.toList()

    val produtos: List<ProductData>
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

    val receita : List<IngredientsFormat>
        get() = _receita.toList()


    fun limparErros() {
        _pedidosErro = null
    }

    fun limparErrosProdutos() {
       _produtosErro = null
    }

    fun limparErrosFormulario() {
        _nomeSolicitanteErro = null
        _telefoneErro = null
        _statusErro = null
        _dataEntregaErro = null
        _itensErro = null
    }

    open fun getPedidos() {}

    open fun getProdutos() {}

    open fun salvarPedido(pedido : OrderCreateData, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun editarPedido(pedido : OrderEditData, idPedido: Int, onBack: () -> Unit, onSucess: () -> Unit) {}

    open fun getReceita(pedido : OrderData) {}
}