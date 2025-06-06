package com.example.gestok.screens.internalScreens.product

import SkeletonLoader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.components.productpage.ProductCard
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.product.ProductApiViewModel

@Composable
fun ProductContent(
    modifier: Modifier = Modifier,
    viewModel: ProductApiViewModel,
    currentPage: MutableState<String>,
    selectedProduct: MutableState<ProductData?>
) {

    val erroProduto = viewModel.produtosErro
    val produtos = viewModel.produtos
    val carregando = viewModel.carregouProdutos

    val erroCategoria = viewModel.categoriasErro
    val categorias = viewModel.categorias

    LaunchedEffect(Unit) {
        viewModel.getCategorias()
        viewModel.getProdutos()
    }

    LazyColumn(
        modifier = modifier
            .background(LightGray)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(R.string.title_product),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black
                    )

                    Button(
                        onClick = {currentPage.value = "stockAdd"},
                        enabled =  viewModel.sessaoUsuario.cargo in listOf("ADMIN", "SUPERVISOR"),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text(stringResource(R.string.button_product_stock), color = White)
                    }

                    Button(
                        onClick = {currentPage.value = "createProduct"},
                        enabled =  viewModel.sessaoUsuario.cargo in listOf("ADMIN", "SUPERVISOR"),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text(stringResource(R.string.button_product_register), color = White)
                    }
                }

                if (erroProduto != null) {
                    Text(
                        erroProduto,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                    )
                }

                if (erroCategoria != null) {
                    Text(
                        erroCategoria,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                    )
                }
            }
        }


        item {
            when {
                !carregando -> {
                    repeat(4) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )
                        ) {
                            SkeletonLoader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(235.dp),
                            )
                        }
                    }
                }

                produtos.isEmpty() -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 250.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.no_products_msg),
                            fontSize = 16.sp,
                            color = Black
                        )
                    }
                }

                else -> {

                    produtos.forEach { produto ->
                        ProductCard(
                            produto = produto,
                            categorias = categorias,
                            currentPage = currentPage,
                            selectedProduct = selectedProduct,
                            viewModel = viewModel,
                            excluirHabilitado = viewModel.sessaoUsuario.cargo in listOf("ADMIN", "SUPERVISOR"),
                            editarHabilitado = viewModel.sessaoUsuario.cargo in listOf("ADMIN", "SUPERVISOR"),
                            producaoHabilitado = viewModel.sessaoUsuario.cargo in listOf("ADMIN", "SUPERVISOR")
                        )
                    }

                }
            }
        }
    }
}


