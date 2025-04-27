package com.example.gestok.components.productpage.dialogs

import android.util.Log
import androidx.compose.foundation.background
import com.example.gestok.components.orderpage.dialogs.PedidoBlock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
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
import com.example.gestok.components.productpage.IngredientData
import com.example.gestok.components.productpage.ProductData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import kotlinx.coroutines.delay


@Composable
fun ProductCreateDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int, String, Double, List<IngredientData>) -> Unit
) {

    var showCreateIngredientDialog by remember { mutableStateOf(false) }
    var ingredientUpdateTrigger by remember { mutableIntStateOf(0) }

    var editedProduto by remember { mutableStateOf("") }
    var editedEstoque by remember { mutableStateOf("") }
    var editedCategoria by remember { mutableStateOf("Selecione uma opção") }
    var editedValor by remember { mutableStateOf("") }
    var editedIngredientes by remember { mutableStateOf(emptyList<IngredientData>()) }

    var showCancelConfirmDialog by remember { mutableStateOf(false) }


    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecione uma opção") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)

            ) {

                // -- HEADER -------------------------
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Text(
                            "Novo Produto",
                            fontWeight = W600,
                            color = Blue,
                            fontSize = 20.sp
                        )

                        Button(onClick = { showCancelConfirmDialog = true }, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = White,

                                )
                        }

                    }
                }
                //--- FOTO DO PRODUTO
                item {

                    Text(
                        "Foto do produto",
                        Modifier.padding(start = 20.dp, bottom = 15.dp),
                        fontWeight = W600,
                        color = Blue
                    )

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally // Centraliza todos os itens dentro da Column
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15))
                                .width(240.dp)
                                .height(240.dp)
                                .background(LightGray)
                                .align(Alignment.CenterHorizontally), // Garante que o Box fique centralizado dentro da Column
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = null,
                                modifier = Modifier.size(50.dp) // Ajusta o tamanho do ícone
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                //--- NOME PRODUTO -------------------------------------------

                item {
                    Column(Modifier.fillMaxWidth()) {

                        Text(
                                "Nome do produto",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )
                        TextField(
                            value = editedProduto,
                            onValueChange = { editedProduto = it },
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

                //--- VALOR -------------------------------------------

                item {
                    Column {
                        Text(
                            "Valor",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )
                        TextField(
                            value = editedValor,
                            onValueChange = { editedValor = it },
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

                //-- ESTOQUE -------------------------------------------

                item {
                    Column {
                        Text(
                            "Estoque",
                            Modifier.padding(start = 20.dp),
                            fontWeight = W600,
                            color = Blue
                        )
                        TextField(
                            value = editedEstoque,
                            onValueChange = { editedEstoque = it },
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


                //-- CATEGORIA -------------------------------------------
                item {
                    Column {
                        Text(
                            "Categoria",
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
//                            .background(Color.Red)
                                .height(0.dp)
                                .width(20.dp)
                        ) {

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .width(240.dp)

//                            .padding(start = 200.dp)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Salgados") },
                                    onClick = {
                                        selectedOption = "Salgados"
                                        expanded = false
                                    })

                                DropdownMenuItem(
                                    text = { Text("Doces") },
                                    onClick = {
                                        selectedOption = "Doces"
                                        expanded = false
                                    })

                                DropdownMenuItem(
                                    text = { Text("Bolos") },
                                    onClick = {
                                        selectedOption = "Bolos"
                                        expanded = false
                                    })
                            }

                        }
                    }
                }

                //-- INGREDIENTES HEADER -------------------------------------------

                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp, start = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Ingredientes",
                            fontWeight = W600,
                            color = Blue,
                            fontSize = 20.sp
                        )
                        Button(onClick = { showCreateIngredientDialog = true},
                            colors = ButtonDefaults.buttonColors(Blue)) {
                            Text(
                                "+ Ingrediente"
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }


                //-- INGREDIENTES -------------------------------------------

                item {
                    Column(Modifier.padding(start = 20.dp, end = 20.dp)) {
                        key(ingredientUpdateTrigger) {
//                            IngredientBlock(editedIngredientes)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                //-- BOTÕES CANCELAR E CONCLUIR ----------------------------------------
                item {
                    Row(Modifier
                        .fillMaxWidth()
                        .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween){
                        Button(onClick = { showCancelConfirmDialog = true }, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                tint = White,

                                )
                            Text("Cancelar", color = White)
                        }
                        Button(onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(LightBlue)) {
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



    if (showCreateIngredientDialog) {
        IngredientCreate(
            onDismiss = {
                showCreateIngredientDialog = false
            },
            onConfirm = { name, quantity, unity ->
                // Handle the confirmed ingredient data here
                println("Ingredient created: $name, $quantity, $unity")
                showCreateIngredientDialog = false
            }
        )
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
