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
import com.example.gestok.components.CancelConfirmationDialog
import com.example.gestok.components.productpage.ProductData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White

@Composable
fun OrderCreate(
    listaProdutos: List<ProductData>,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, List<String>) -> Unit
) {
    var nomeSolicitante by remember { mutableStateOf("") }
    var contato by remember { mutableStateOf("") }
    var statusPedido by remember { mutableStateOf("Selecione uma opção") }
    var dataEntrega by remember { mutableStateOf("") }
    var valorPedido by remember { mutableStateOf("") }
    var itens by remember { mutableStateOf(emptyList<String>()) }

    var showCancelConfirmDialog by remember { mutableStateOf(false) }
    var showAddItemDialog by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecione uma opção") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {

                // -- HEADER -------------------------
                item {
                    Row(
                        Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Criar Pedido", fontWeight = W600, color = Blue, fontSize = 20.sp)
                        Button(onClick = { showCancelConfirmDialog = true }, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = White)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                //-- SOLICITANTE ----------------------
                item {
                    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                        Text("Solicitante", fontWeight = W600, color = Blue)
                        TextField(
                            value = nomeSolicitante,
                            onValueChange = { nomeSolicitante = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray,
                                unfocusedContainerColor = LightGray,
                                focusedTextColor = Black,
                                unfocusedTextColor = Black,
                                focusedIndicatorColor = LightGray,
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier.clip(RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                //-----STATUS--------------------
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

                //-----DATA----------------
                item {
                    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                        Text("Data de Entrega", fontWeight = W600, color = Blue)
                        TextField(
                            value = dataEntrega,
                            onValueChange = { dataEntrega = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray,
                                unfocusedContainerColor = LightGray,
                                focusedTextColor = Black,
                                unfocusedTextColor = Black,
                                focusedIndicatorColor = LightGray,
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier.clip(RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                //-----VALOR----------------------
                item {
                    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                        Text("Valor", fontWeight = W600, color = Blue)
                        TextField(
                            value = valorPedido,
                            onValueChange = { valorPedido = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray,
                                unfocusedContainerColor = LightGray,
                                focusedTextColor = Black,
                                unfocusedTextColor = Black,
                                focusedIndicatorColor = LightGray,
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier.clip(RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                //--- HEADER "ITENS" + BOTAO ADICIONAR -----------------
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
                        Button(onClick = {showAddItemDialog = true}, colors = ButtonDefaults.buttonColors(LightBlue)) {
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
                    Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = { showCancelConfirmDialog = true }, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null, tint = White)
                            Text("Cancelar", color = White)
                        }
                        Button(onClick = { onConfirm(nomeSolicitante, contato, statusPedido, dataEntrega, itens) }, colors = ButtonDefaults.buttonColors(LightBlue)) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = White)
                            Text("Criar", color = White)
                        }
                    }
                }
            }
        }
    }

    if(showAddItemDialog){
        ItensAdd(produtos = listaProdutos,
            onDismiss = { showAddItemDialog = false },
            onConfirm = {showAddItemDialog = false})
    }

    if(showCancelConfirmDialog){
        CancelConfirmationDialog(
            onDismiss = {
                showCancelConfirmDialog = false
            },
            onConfirm = {
                showCancelConfirmDialog = false
            },
            externalOnDismiss = {onDismiss()}
        )
    }
}