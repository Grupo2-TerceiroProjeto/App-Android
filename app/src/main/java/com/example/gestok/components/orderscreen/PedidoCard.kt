package com.example.gestok.components.orderscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PedidoCard(
    nomeSolicitante: String,
    contato: String,
    statusPedido: String,
    dataEntrega: String,
    itens: List<String>
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFF),
        ),

        border = BorderStroke(1.dp, Color.Gray)

    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Text(text = "Solicitante", fontWeight = FontWeight.Bold, color = Color(0xFF196BAD))
                    Text(text = nomeSolicitante, fontWeight = FontWeight.W300, color = Color(0xFF196BAD))

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = "Contato", fontWeight = FontWeight.Bold, color = Color(0xFF196BAD))
                    Text(text = contato, fontWeight = FontWeight.W300,  color = Color(0xFF196BAD))
                }

                Column {
                    Text(text = "Status do pedido:", fontWeight = FontWeight.Bold, color = Color(0xFF196BAD))
                    Text(text = statusPedido, fontWeight = FontWeight.W300,  color = Color(0xFF196BAD))

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = "Data entrega", fontWeight = FontWeight.Bold, color = Color(0xFF196BAD))
                    Text(text = dataEntrega, fontWeight = FontWeight.W300,  color = Color(0xFF196BAD))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                itens.forEach { item ->
                    Text(text = "• $item", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

        }

    }

}
