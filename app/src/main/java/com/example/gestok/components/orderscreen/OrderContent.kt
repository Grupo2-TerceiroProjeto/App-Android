package com.example.gestok.components.orderscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.gestok.teste

@Composable
fun OrderContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        items(3) { // Se o número de pedidos for dinâmico, use a lista diretamente
            PedidoCard(
                nomeSolicitante = "Luca Sena",
                contato = "11984199966",
                statusPedido = "Entregue",
                dataEntrega = "19/03/2025",
                itens = teste
            )
        }
    }
}