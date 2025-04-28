package com.example.gestok.viewModel.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestok.screens.internalScreens.order.data.OrderData
import com.example.gestok.screens.internalScreens.order.data.ProductData
import com.example.gestok.screens.login.data.UserSession

abstract class OrderViewModel(open val sessaoUsuario : UserSession) : ViewModel() {

    protected var _pedidosErro by mutableStateOf<String?>(null)

    protected var _produtosErro by mutableStateOf<String?>(null)

    protected var _pedidos = mutableListOf<OrderData>()

    protected var _produtos = mutableListOf<ProductData>()

    protected var _carregouPedidos by mutableStateOf(false)

    protected var _carregouProdutos by mutableStateOf(false)

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

    fun limparErros() {
        _pedidosErro = null
    }

    fun limparErrosProdutos() {
       _produtosErro = null
    }

    open fun getPedidos() {}

    open fun getProdutos() {}
}