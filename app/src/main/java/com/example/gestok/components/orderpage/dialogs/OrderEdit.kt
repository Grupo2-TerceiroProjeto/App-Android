package com.example.gestok.components.orderpage.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gestok.components.orderpage.OrderItens
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White


@Composable
fun EditarPedidoDialog(
    nomeSolicitante: String,
    telefone: String,
    status: String,
    dataEntrega: String,
    produtos: List<OrderItens>,
    totalCompra: Double,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, List<String>) -> Unit
) {
    var editedNomeSolicitante by remember { mutableStateOf(nomeSolicitante) }
    var editedContato by remember { mutableStateOf(telefone) }
    var editedStatusPedido by remember { mutableStateOf(status) }
    var editedDataEntrega by remember { mutableStateOf(dataEntrega) }
    var editedValorPedido by remember { mutableStateOf(totalCompra.toString()) }
    val editedItens by remember { mutableStateOf(produtos) }

    var expanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)

            ) {

                //----- HEADER ------------------------------------------------------------------------
                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Editar pedidos",
                            fontWeight = W600,
                            color = Blue,
                            fontSize = 20.sp
                        )
                        Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = White,

                                )
                        }
                    }
                }
                //-----SOLICITANTE-------------------------------------------------------------------------
                item {
                    Column(Modifier.fillMaxWidth()) {

                        Text(
                            "Solicitante",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )
                        TextField(
                            value = editedNomeSolicitante,
                            onValueChange = { editedNomeSolicitante = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray, // Cor de fundo quando focado
                                unfocusedContainerColor = LightGray,   // Cor de fundo quando não focado
                                focusedTextColor = Black,          // Cor do texto quando focado
                                unfocusedTextColor = Black,         // Cor do texto quando não focado
                                focusedIndicatorColor = LightGray, // Cor do indicador (borda) quando focado
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 15.dp)
                                .clip(shape = RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                }

                //-----STATUS-------------------------------------------------------------------------
                item {
                    Column {
                        Text(
                            "Status do Pedido",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            TextField(
                                value = editedStatusPedido,
                                onValueChange = {},
                                readOnly = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = LightGray, // Cor de fundo quando focado
                                    unfocusedContainerColor = LightGray,   // Cor de fundo quando não focado
                                    focusedTextColor = Black,          // Cor do texto quando focado
                                    unfocusedTextColor = Black,         // Cor do texto quando não focado
                                    focusedIndicatorColor = LightGray, // Cor do indicador (borda) quando focado
                                    unfocusedIndicatorColor = LightGray
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp, end = 10.dp)
                                    .width(240.dp)
                                    .clip(RoundedCornerShape(20)),

                                singleLine = true
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Blue,

                                modifier = Modifier
                                    .clickable { expanded = !expanded }
                                    .scale(2.0F, 2.0F)


                            )
                        }

                        Box(
                            Modifier
                                .padding(start = 20.dp, bottom = 15.dp)

                                .height(0.dp)
                                .width(20.dp)
                        ) {

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.width(240.dp)
                            ) {
                                listOf(
                                    "Pendente",
                                    "Em Produção",
                                    "Concluído",
                                    "Cancelado"
                                ).forEach { statusOption ->
                                    DropdownMenuItem(
                                        text = { Text(statusOption) },
                                        onClick = {
                                            editedStatusPedido = statusOption
                                            expanded = false
                                        }
                                    )
                                }

                            }

                        }
                    }
                }

                //-----CONTATO-------------------------------------------------------------------------
                item {
                    Column(Modifier.fillMaxWidth()) {

                        Text(
                            "Contato",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )
                        TextField(
                            value = editedContato,
                            onValueChange = { editedContato = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray, // Cor de fundo quando focado
                                unfocusedContainerColor = LightGray,   // Cor de fundo quando não focado
                                focusedTextColor = Black,          // Cor do texto quando focado
                                unfocusedTextColor = Black,         // Cor do texto quando não focado
                                focusedIndicatorColor = LightGray, // Cor do indicador (borda) quando focado
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 15.dp)
                                .clip(shape = RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                }

                //-----DATA-------------------------------------------------------------------------
                item {
                    Column {
                        Text(
                            "Data de Entrega",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )
                        TextField(
                            value = editedDataEntrega,
                            onValueChange = { editedDataEntrega = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray, // Cor de fundo quando focado
                                unfocusedContainerColor = LightGray,   // Cor de fundo quando não focado
                                focusedTextColor = Black,          // Cor do texto quando focado
                                unfocusedTextColor = Black,         // Cor do texto quando não focado
                                focusedIndicatorColor = LightGray, // Cor do indicador (borda) quando focado
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 15.dp)
                                .clip(shape = RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                }

                //-----VALOR-------------------------------------------------------------------------
                item {
                    Column {
                        Text(
                            "Valor",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )
                        TextField(
                            value = editedValorPedido,
                            onValueChange = { editedValorPedido = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray, // Cor de fundo quando focado
                                unfocusedContainerColor = LightGray,   // Cor de fundo quando não focado
                                focusedTextColor = Black,          // Cor do texto quando focado
                                unfocusedTextColor = Black,         // Cor do texto quando não focado
                                focusedIndicatorColor = LightGray, // Cor do indicador (borda) quando focado
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 15.dp)
                                .clip(shape = RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                }
                //--- HEADER "ITENS" + BOTAO ADICIONAR -------------------------------------------------

                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp, start = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Itens",
                            fontWeight = W600,
                            color = Blue,
                            fontSize = 20.sp
                        )
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(LightBlue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = White,

                                )
                            Text("  Adicionar", color = White)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }

                //-- ITENS + QUANTIDADE -----------------------------

                item {
                    Column(Modifier.padding(start = 20.dp, end = 20.dp)) {
                        PedidoBlock(editedItens)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }


                //-- BOTÕES CANCELAR E CONCLUIR ----------------------------------------

                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(Blue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                tint = White,

                                )
                            Text("Cancelar", color = White)
                        }
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(LightBlue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = White,

                                )
                            Text("Concluir", color = White)
                        }
                    }
                }

            }

        }
    }
}

