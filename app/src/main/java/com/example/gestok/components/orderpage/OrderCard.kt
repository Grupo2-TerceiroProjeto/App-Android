package com.example.gestok.components.orderpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.components.orderpage.dialogs.EditarPedidoDialog
import com.example.gestok.ui.theme.Blue

@Composable
fun OrderCard(pedido: OrderData) {

    var showEditDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, //COR DO CARD
        ),

//        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Solicitante",
                        fontWeight = FontWeight.Bold,
                        color = Blue
                    )
                    Text(
                        text = pedido.nomeSolicitante,
                        fontWeight = FontWeight.W300,
                        color = Blue
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = "Contato", fontWeight = FontWeight.Bold, color = Blue)
                    Text(text = pedido.contato, fontWeight = FontWeight.W300, color = Blue)
                }

                Column {
                    Text(
                        text = "Status do pedido",
                        fontWeight = FontWeight.Bold,
                        color = Blue
                    )
                    Text(
                        text = pedido.statusPedido,
                        fontWeight = FontWeight.W300,
                        color = Blue
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Data entrega",
                        fontWeight = FontWeight.Bold,
                        color = Blue
                    )
                    Text(
                        text = pedido.dataEntrega,
                        fontWeight = FontWeight.W300,
                        color = Blue
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Column(Modifier.padding(top = 16.dp)) {
                Row {
                    Column {
                        pedido.itens.forEach { (item, quantidade) ->
                            Text(
                                text = "•${quantidade} | ${item.produto}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500,
                                color = Blue
                            )
                        }
                    }
                    Column (Modifier
                        .fillMaxWidth()
                        .align(Alignment.Bottom),
                        horizontalAlignment = Alignment.End){
                        Row (){
                            IconButton(onClick = {showEditDialog = true},
                                modifier = Modifier
                                    .size(50.dp)
//                                    .height(50.dp)
//                                    .weight(1f)

                            ) {
                               Image(
                                   painter = painterResource(id = R.drawable.edicao_f),
                                   contentDescription = "Editar",


                               )



                           }

                            IconButton(onClick = {},
                                modifier = Modifier
                                    .height(50.dp)
//                                    .weight(1f)

                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.menu_f),
                                    contentDescription = "Informação",


                                )
                            }
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

        }

    }



    if(showEditDialog){
        EditarPedidoDialog(
            order = pedido,
            onDismiss = { showEditDialog = false },
            onConfirm = { newNome, newContato, newStatus, newData, newItens ->
                showEditDialog = false
            }
        )
    }



}




