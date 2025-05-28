package com.example.gestok.components.productpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.screens.internalScreens.product.data.IngredientsData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.MediumGray
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.product.ProductApiViewModel

@Composable
fun IngredientAdd(
    viewModel: ProductApiViewModel,
    onConfirm: (List<IngredientsData>) -> Unit
) {

    var selectedIngredients by remember { mutableStateOf(setOf<IngredientsData>()) }

    val errosIngrediente = viewModel.ingredientesErro
    val ingredientes = viewModel.ingredientes
    val carregando = viewModel.carregouIngredientes

    LaunchedEffect(Unit) {
        viewModel.getIngredientes()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!carregando) Modifier.height(200.dp)
                else if (ingredientes.isEmpty()) Modifier.height(200.dp)
                else Modifier.height(320.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {

            Text(
                "Todos os ingredientes",
                Modifier.padding(start = 20.dp),
                color = Blue,
                fontWeight = W600,
                fontSize = 16.sp
            )

            if (errosIngrediente != null)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        text = errosIngrediente,
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
                        .height(200.dp)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Carregando Ingredientes...", color = MediumGray)
                    }
                }
            }

            ingredientes.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text("Nenhum ingrediente cadastrado", color = MediumGray)
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp)
                ) {
                    items(ingredientes) { ingrediente ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(LightGray)
                                .toggleable(
                                    value = selectedIngredients.contains(ingrediente),
                                    onValueChange = {
                                        selectedIngredients = if (it) {
                                            selectedIngredients + ingrediente
                                        } else {
                                            selectedIngredients - ingrediente
                                        }
                                    }
                                )
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Checkbox(
                                checked = selectedIngredients.contains(ingrediente),
                                onCheckedChange = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(ingrediente.nome, color = Black)
                        }
                    }
                }
            }
        }

        if(carregando) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onConfirm(selectedIngredients.toList()) },
                    colors = ButtonDefaults.buttonColors(Blue),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text("Adicionar", color = White)
                }
            }
        }
    }

}





