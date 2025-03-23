package com.example.gestok.components.orderscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gestok.components.teste
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.White



@Composable
fun OrderContent(modifier: Modifier = Modifier, lista: List<String>) {


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))

    ) {

        item {
            Column (modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp, top = 15.dp)){
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Pedidos", fontWeight = FontWeight.W600, color = Black)
                    Button(onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                        ) {
                        Text("Cadastrar pedido", color = White)
                    }
                }

            }

        }

        items(3) {
            PedidoCard(
                nomeSolicitante = "Luca Sena",
                contato = "11984199966",
                statusPedido = "Entregue",
                dataEntrega = "19/03/2025",
                itens = lista
            )
        }
    }
}