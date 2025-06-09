package com.example.gestok.presentation.component.productpage.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gestok.R
import com.example.gestok.presentation.component.InputLabelDisable
import com.example.gestok.domain.model.product.IngredientsRecipe
import com.example.gestok.domain.model.product.NutrientesResponse
import com.example.gestok.domain.model.product.ProductModel
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.presentation.viewmodel.product.ProductApiViewModel
import java.util.Locale


@Composable
fun NutritionalDataDialog(
    product: ProductModel,
    viewModel: ProductApiViewModel,
    onDismissRequest: () -> Unit
) {
    var ingredients by remember { mutableStateOf(emptyList<IngredientsRecipe>()) }
    var nutrients by remember { mutableStateOf(emptyList<NutrientesResponse>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(product) {
        isLoading = true
        nutrients = emptyList()

        viewModel.getReceita(product) { listaIngredientes ->
            ingredients = listaIngredientes

            viewModel.getNutrientes(listaIngredientes) {
                nutrients = viewModel.nutrientes!!
                isLoading = false
            }
        }
    }

    val totalPesoIngredientes = ingredients.sumOf { it.quantidade ?: 0.0 }
    val totalPesoFormatado = String.format(Locale.US, "%.2f", totalPesoIngredientes)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(R.string.title_product_nutritional_data, product.nome),
                        modifier = Modifier.weight(0.6f),
                        fontWeight = FontWeight.Bold,
                        color = Blue,
                        fontSize = 16.sp
                    )
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = Blue,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (isLoading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.loading_product_nutritional_data),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                } else {

                    if (ingredients.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            Text(
                                stringResource(R.string.product_ingredients_text),
                                fontWeight = FontWeight.Bold,
                                color = Blue,
                                modifier = Modifier
                                    .padding(bottom = 24.dp)
                            )

                            Text(
                                text = stringResource(R.string.no_ingredients_nutritional_data_msg),
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    } else {
                        InputLabelDisable(
                            text =  stringResource(R.string.product_ingredients_text),
                            value = ingredients.joinToString(", ") { it.nome },
                            singleLine = false,
                            icon = false
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        stringResource(R.string.title_table_nutritional_data_txt),
                        fontWeight = FontWeight.Bold,
                        color = Blue,
                        fontSize = 14.sp
                    )

                    if (nutrients.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text =   stringResource(R.string.no_nutritional_data_msg),
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    } else {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text(
                                        "$totalPesoFormatado g",
                                        color = LightBlue,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }

                                Divider(modifier = Modifier.padding(vertical = 4.dp))

                                val nutrientesAgrupados = nutrients
                                    .groupBy { it.tipo.lowercase() }
                                    .mapValues { entry ->
                                        entry.value.sumOf {
                                            it.pcComposicao.toDoubleOrNull() ?: 0.0
                                        }
                                    }

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 250.dp)
                                ) {

                                    items(nutrientesAgrupados.entries.toList()) { (tipo, valorTotal) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = tipo.replaceFirstChar { it.uppercase() },
                                                color = LightBlue,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "%.2f".format(valorTotal),
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
