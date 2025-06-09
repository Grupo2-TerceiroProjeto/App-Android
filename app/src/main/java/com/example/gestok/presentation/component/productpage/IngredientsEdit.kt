package com.example.gestok.presentation.component.productpage

import com.example.gestok.presentation.component.SelectOption
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.presentation.component.InputLabel
import com.example.gestok.domain.model.product.IngredientsBody
import com.example.gestok.domain.model.product.IngredientsModel
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White
import com.example.gestok.presentation.viewmodel.product.ProductApiViewModel

@Composable
fun IngredientsEdit(
    viewModel: ProductApiViewModel,
    onBack: () -> Unit,
    ingrediente: IngredientsModel,
) {
    val medidasMap = mapOf(
        1 to "g",
        2 to "ml",
        3 to "kg",
        4 to "un"
    )

    var nome by remember { mutableStateOf(ingrediente.nome) }
    var quantidade by remember { mutableIntStateOf(ingrediente.quantidade?.toInt() ?: 0) }
    var medida by remember { mutableIntStateOf(ingrediente.medida.toInt()) }
    var quantidadeTexto by remember { mutableStateOf(ingrediente.quantidade.toInt().toString()) }

    val ingredienteEditado = IngredientsBody(
        nome = nome,
        quantidade = quantidade,
        medida = medida.toDouble()
    )

    LaunchedEffect(Unit) {
        viewModel.limparErrosFormularioIngrediente()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(515.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Spa,
                    contentDescription = "Ingrediente",
                    tint = Color(0xFF26A2C6),
                    modifier = Modifier
                        .size(28.dp)
                )

                Text(
                    stringResource(R.string.tile_ingredient_edit),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {

                Column {
                    InputLabel(
                        text = stringResource(R.string.label_name),
                        value = nome,
                        onValueChange = {
                            val filtered =
                                it.filter { char -> char.isLetter() || char.isWhitespace() }
                            nome = filtered
                        },
                        keyboardType = KeyboardType.Text,
                        erro = viewModel.nomeIngredienteErro,
                        maxLength = 45,
                    )
                }

                Column {
                    InputLabel(
                        text = stringResource(R.string.label_ingredient_amount),
                        value = quantidadeTexto,
                        onValueChange = {
                            quantidadeTexto = it
                            quantidade = it.toIntOrNull() ?: 0
                        },
                        keyboardType = KeyboardType.Number,
                        erro = viewModel.quantidadeIngredienteErro,
                        maxLength = 15
                    )
                }

                Column {
                    var medidaSelecionada = medidasMap[medida.toInt()] ?: ""

                    SelectOption(
                        text = stringResource(R.string.label_ingredient_measure),
                        value = medidaSelecionada,
                        onValueChange = { selectedMedida ->
                            medidaSelecionada = selectedMedida
                            val idSelecionado = medidasMap.entries.find { it.value == selectedMedida }?.key
                            medida = idSelecionado ?: 0
                        },
                        list = medidasMap.values.toList(),
                        erro = viewModel.medidaIngredienteErro
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Button(
                            onClick = {onBack()},
                            colors = ButtonDefaults.buttonColors(LightBlue),
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardReturn,
                                contentDescription = null,
                                tint = White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.button_back_text), color = White, fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = { viewModel.editarIngrediente(ingrediente.id, ingredienteEditado, onBack) },
                            colors = ButtonDefaults.buttonColors(Blue),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.button_ingredient_edit_txt), color = White, fontSize = 16.sp)
                        }


                    }

                    if (viewModel.edicaoIngredienteErro != null) {
                        Text(
                            viewModel.edicaoIngredienteErro!!,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFD32F2F),
                        )
                    }

                }
            }

        }

    }

}