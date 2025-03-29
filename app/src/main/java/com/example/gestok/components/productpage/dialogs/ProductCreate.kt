package com.example.gestok.components.productpage.dialogs

import com.example.gestok.components.orderpage.dialogs.PedidoBlock

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
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White


@Composable
fun CriarPedidoDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, List<String>) -> Unit
) {
    var editedNomeSolicitante by remember { mutableStateOf("") }
    var editedContato by remember { mutableStateOf("") }
    var editedStatusPedido by remember { mutableStateOf("") }
    var editedDataEntrega by remember { mutableStateOf("") }
    var editedValorPedido by remember { mutableStateOf("") }


    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecione uma opção") }

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
                            "Criar Produto",
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
                                value = selectedOption,
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
                                modifier = Modifier
                                    .width(240.dp)


                            ) {
                                DropdownMenuItem(
                                    text = { Text("Em produção") },
                                    onClick = {
                                        selectedOption = "Em producão"
                                        expanded = false
                                    })

                                DropdownMenuItem(
                                    text = { Text("Entregue") },
                                    onClick = {
                                        selectedOption = "Entregue"
                                        expanded = false
                                    })

                                DropdownMenuItem(
                                    text = { Text("Cancelado") },
                                    onClick = {
                                        selectedOption = "Cancelado"
                                        expanded = false
                                    })
                            }

                        }
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
                        Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(LightBlue)) {
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

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }


                //-- BOTÕES CANCELAR E CONCLUIR ----------------------------------------

                item {
                    Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween){
                        Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                tint = White,

                                )
                            Text("Cancelar", color = White)
                        }
                        Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(LightBlue)) {
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

