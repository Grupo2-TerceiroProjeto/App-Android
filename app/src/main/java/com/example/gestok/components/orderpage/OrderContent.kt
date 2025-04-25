package com.example.gestok.components.orderpage

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.orderpage.dialogs.OrderCreate
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.White



@Composable
fun OrderContent(modifier: Modifier = Modifier, pedido: OrderData) {

    var showCreateDialog by remember { mutableStateOf(false) }

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
                    Text("Pedidos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black)
                    Button(onClick = {showCreateDialog = true},
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                        ) {
                        Text("Cadastrar pedido", color = White)
                    }
                }

            }

        }

        items(3) {
            OrderCard(pedido = pedido)
        }
    }

    if (showCreateDialog) {
        OrderCreate(
            onDismiss = { showCreateDialog = false },
            onConfirm = { newNome, newContato, newStatus, newData, newItens ->
                showCreateDialog = false
            }
        )
    }
}