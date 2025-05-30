package com.example.gestok.components.productpage

import SelectOption
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
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.InputLabel
import com.example.gestok.screens.internalScreens.product.data.IngredientsCreate
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.White

@Composable
fun IngredientCreate(
    onSalvar: () -> Unit
) {

    val medidasMap = mapOf(
        1 to "g",
        2 to "ml",
        3 to "kg",
        4 to "unidade"
    )

    var nome by remember { mutableStateOf("") }
    var quantidade by remember { mutableIntStateOf(0) }
    var medida by remember { mutableDoubleStateOf(0.0) }
    var quantidadeTexto by remember { mutableStateOf("") }
    var medidaSelecionada by remember { mutableStateOf("") }

    val novoIngrediente = IngredientsCreate(
        nome = nome,
        quantidade = quantidade,
        medida = medida
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp),
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
                    "Novo Ingrediente",
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
                        text = "Nome",
                        value = nome,
                        onValueChange = {
                            val filtered =
                                it.filter { char -> char.isLetter() || char.isWhitespace() }
                            nome = filtered
                        },
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                        maxLength = 45,
                    )
                }

                Column {
                    InputLabel(
                        text = "Quantidade",
                        value = quantidadeTexto,
                        onValueChange = {
                            quantidadeTexto = it
                            quantidade = it.toIntOrNull() ?: 0
                        },
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                        maxLength = 15
                    )
                }

                Column {
                    SelectOption(
                        text = "Medida",
                        value = if (medidaSelecionada.isEmpty()) "Selecione uma opção" else medidaSelecionada,
                        onValueChange = { selectedMedida ->
                            medidaSelecionada = selectedMedida
                            val idSelecionado =
                                medidasMap.entries.find { it.value == selectedMedida }?.key
                            medida = idSelecionado?.toDouble() ?: 0.0
                        },
                        list = medidasMap.values.toList()
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { onSalvar() },
                            colors = ButtonDefaults.buttonColors(Blue),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Criar Ingrediente", color = White, fontSize = 16.sp)
                        }
                    }
                }

            }
        }

    }

}
