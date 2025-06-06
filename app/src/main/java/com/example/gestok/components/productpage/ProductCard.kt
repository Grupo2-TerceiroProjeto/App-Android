package com.example.gestok.components.productpage

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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import com.example.gestok.R
import com.example.gestok.components.ExcludeConfirmationDialog
import com.example.gestok.components.productpage.popups.NutritionalDataDialog
import com.example.gestok.screens.internalScreens.product.data.CategoryData
import com.example.gestok.screens.internalScreens.product.data.ProductData
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.viewModel.product.ProductApiViewModel

@Composable
fun ProductCard(
    produto: ProductData,
    categorias: List<CategoryData>,
    currentPage: MutableState<String>,
    selectedProduct: MutableState<ProductData?>,
    viewModel: ProductApiViewModel,
    excluirHabilitado: Boolean,
    editarHabilitado: Boolean,
    producaoHabilitado: Boolean
){

    var showNutritionalDialog by remember { mutableStateOf(false) }
    var showExcludeConfirmDialog by remember { mutableStateOf(false) }


    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)){
            Row(
                modifier =  Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1F)){
                    Text(
                        text = produto.nome,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF196BAD)
                    )
                }
                Row () {
                    IconButton(
                        onClick = {showNutritionalDialog = true},
                        modifier = Modifier
                            .size(50.dp)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.info_f),
                            contentDescription = "Informação Nutricional"
                        )

                    }

                    IconButton(
                        onClick = {
                            selectedProduct.value = produto
                            currentPage.value = "editProduct"},
                        enabled = editarHabilitado,
                        modifier = Modifier
                            .height(50.dp)


                    ) {
                        if(!editarHabilitado) {
                            Image(
                                painter = painterResource(id = R.drawable.edicao_disable),
                                contentDescription = "Editar",
                            )
                        }else {
                            Image(
                                painter = painterResource(id = R.drawable.edicao_f),
                                contentDescription = "Editar"
                            )
                        }
                    }

                    IconButton(
                        onClick = {showExcludeConfirmDialog = true},
                        enabled = excluirHabilitado,
                        modifier = Modifier
                            .height(50.dp)


                    ) {
                        if(!excluirHabilitado) {
                            Image(
                                painter = painterResource(id = R.drawable.trashcan_disable),
                                contentDescription = "Excluir",
                            )
                        }else {

                            Image(
                                painter = painterResource(id = R.drawable.trashcan_f),
                                contentDescription = "Excluir",
                            )
                        }
                    }

                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Row (Modifier.padding(top = 16.dp)){
                Column (Modifier.padding(end = 30.dp)){
                    Text(stringResource(R.string.button_product_stock),
                        fontWeight = FontWeight.Bold,
                        color = Blue)

                    Text(text = produto.quantidade.toString(),
                        fontWeight = FontWeight.W300,
                        color = Blue)
                }

                Column {
                    Text(stringResource(R.string.label_product_categories),
                        fontWeight = FontWeight.Bold,
                        color = Blue)

                    val nomeCategoria = categorias.find { it.id == produto.categoria }?.nome ?: "N/A"

                    Text(text = nomeCategoria,
                        fontWeight = FontWeight.W300,
                        color = Blue)
                }

            }

            val isAtualizando = viewModel.isUpdatingMap[produto.id] ?: false
            val habilitado = producaoHabilitado && !isAtualizando

            Row(
                modifier = Modifier.padding(top = 25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

                Switch(
                    checked = produto.emProducao,
                    onCheckedChange = {
                        if (habilitado) {
                            viewModel.atualizarProducao(produto)
                        }
                    },
                    enabled = habilitado,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = LightGray,
                        checkedTrackColor = Blue,
                        uncheckedThumbColor = Blue,
                        uncheckedTrackColor = LightGray
                    )
                )

                Text(when(produto.emProducao){
                    true -> stringResource(R.string.in_production_product_txt)
                    else -> stringResource(R.string.no_production_product_txt)},
                    color = Blue,
                    fontWeight = W600,
                    modifier = Modifier.padding(start = 16.dp)

                )

            }
        }

    }

    if (showNutritionalDialog) {
        NutritionalDataDialog(
            product = produto,
            viewModel = viewModel,
            onDismissRequest = { showNutritionalDialog = false }
        )
    }

    if(showExcludeConfirmDialog){
        ExcludeConfirmationDialog(
            onDismiss = { showExcludeConfirmDialog = false },
            onConfirm = {
                viewModel.deletarProduto(produto.id, onBack = {
                    showExcludeConfirmDialog = false
                })
            }
        )
    }

}