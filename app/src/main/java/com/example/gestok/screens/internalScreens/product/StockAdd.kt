package com.example.gestok.screens.internalScreens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.components.orderpage.ItensBlock
import com.example.gestok.components.productpage.ProductAdd
import com.example.gestok.screens.internalScreens.order.data.OrderItensBlock
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.product.ProductApiViewModel


@Composable
fun StockAdd(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSucess: () -> Unit,
    viewModel: ProductApiViewModel
) {

    var produtos by remember { mutableStateOf(emptyList<ProductData>()) }
    var productAdd by remember { mutableStateOf(false) }

    val updateQuantidade: (Int, Int) -> Unit = { index, newQuantidade ->
        produtos = produtos.toMutableList().apply {
            this[index] = this[index].copy(quantidade = newQuantidade)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.limparErrosFormulario()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF005BA4), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }

                    Text(
                        stringResource(R.string.title_update_stock),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f)
                    )
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

        }

        if (!productAdd) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.title_product),
                        fontWeight = W600,
                        color = Blue,
                        fontSize = 18.sp
                    )
                    Button(
                        onClick = { productAdd = true },
                        colors = ButtonDefaults.buttonColors(LightBlue)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = White,

                            )
                        Text("  " + stringResource(R.string.to_add_text), color = White)
                    }
                }


                if (viewModel.itensErro != null) {
                    Text(
                        viewModel.itensErro!!,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        color = Color(0xFFD32F2F),
                        modifier = Modifier.padding(
                            start = 20.dp,
                            top = 32.dp
                        ),
                    )
                }


                if (produtos.isEmpty()) {
                    Text(
                        stringResource(R.string.stock_add_empty_products_msg),
                        fontSize = 14.sp,
                        color = Black,
                        modifier = Modifier.padding(
                            start = 50.dp,
                            end = 50.dp,
                            top = 200.dp,
                            bottom = 32.dp
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 24.dp)
                            .height(410.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(produtos.size) { index ->
                            val item = produtos[index]
                            ItensBlock(
                                listOf(OrderItensBlock(nome = item.nome, quantidade = item.quantidade)),
                                updateQuantidade = { _, newQtd -> updateQuantidade(index, newQtd) },
                                onExcluirClick = {
                                    produtos = produtos.toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                            )
                        }
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {viewModel.atualizarEstoque(produtos, onBack, onSucess)},
                                colors = ButtonDefaults.buttonColors(Blue),
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(stringResource(R.string.button_save_text), color = White, fontSize = 16.sp)
                            }

                            if (viewModel.estoqueErro != null) {
                                Text(
                                    viewModel.estoqueErro!!,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFFD32F2F),
                                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        if (productAdd) {
            item {
                ProductAdd(
                    viewModel,
                    onConfirm = { selectedProducts ->
                        produtos = produtos + selectedProducts
                            .filter { selected ->
                                produtos.none { it.id == selected.id }
                            }
                            .map {
                                ProductData(
                                    id = it.id,
                                    nome = it.nome,
                                    categoria = it.categoria,
                                    preco = it.preco,
                                    quantidade = it.quantidade,
                                    emProducao = it.emProducao,
                                    imagem = it.imagem,
                                )
                            }
                        productAdd = false
                    }
                )
            }
        }

    }



}