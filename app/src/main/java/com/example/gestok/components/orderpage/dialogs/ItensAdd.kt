package com.example.gestok.components.orderpage.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gestok.screens.internalScreens.order.data.ProductData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.MediumGray
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.order.OrderApiViewModel

@Composable
fun ItensAdd(
    onDismiss: () -> Unit,
    onConfirm: (List<ProductData>) -> Unit,
    viewModel: OrderApiViewModel
) {
    var selectedProducts by remember { mutableStateOf(setOf<ProductData>()) }

    val errosProduto = viewModel.produtosErro
    val produtos = viewModel.produtos
    val carregando = viewModel.carregouProdutos

    LaunchedEffect(Unit) {
        viewModel.getProdutos()
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 730.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Produtos", fontWeight = W600, color = Blue, fontSize = 24.sp)
                    Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Blue)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = White,

                            )
                    }
                }

                Text(
                    "Todos os produtos",
                    Modifier.padding(start = 20.dp, bottom = 10.dp),
                    color = Blue,
                    fontWeight = W600,
                    fontSize = 16.sp
                )

                if (errosProduto != null)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            text = errosProduto,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
            }


            when {
                !carregando -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Carregando Produtos...", color = MediumGray)
                        }
                    }
                }

                produtos.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text("Nenhum produto encontrado", color = MediumGray)
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 20.dp)
                    ) {
                        items(produtos) { produto ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(LightGray)
                                    .toggleable(
                                        value = selectedProducts.contains(produto),
                                        onValueChange = {
                                            selectedProducts = if (it) {
                                                selectedProducts + produto
                                            } else {
                                                selectedProducts - produto
                                            }
                                        }
                                    )
                                    .padding(horizontal = 12.dp, vertical = 10.dp)
                            ) {
                                Checkbox(
                                    checked = selectedProducts.contains(produto),
                                    onCheckedChange = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(produto.nome, color = Black)
                            }
                        }
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onConfirm(selectedProducts.toList()) },
                    colors = ButtonDefaults.buttonColors(Blue),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text("Adicionar", color = White)
                }
            }
        }
    }
}
