package com.example.gestok.components.productpage.dialogs


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

@Composable
fun OrderEdit(
    product: ProductData,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, String, Double, List<IngredientData>) -> Unit
) {
    var editedProduto by remember { mutableStateOf(product.produto) }
    var editedEstoque by remember { mutableStateOf(product.estoque.toString()) }
    var editedCategoria by remember { mutableStateOf(product.categoria) }
    var editedValor by remember { mutableStateOf(product.valor.toString()) }
    var editedIngredientes by remember { mutableStateOf(product.ingredientes) }

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
                    .height(700.dp)

            ) {

                // -- HEADER -------------------------
                item {
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Text(
                            "Editar Produto",
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
                                            }
                    Spacer(modifier = Modifier.height(10.dp))

                }


                //-- INGREDIENTES -------------------------------------------

                item {
                    Column(Modifier.padding(start = 20.dp, end = 20.dp)) {
                        IngredientBlock(product)
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