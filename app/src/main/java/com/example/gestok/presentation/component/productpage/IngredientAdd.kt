package com.example.gestok.presentation.component.productpage

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.domain.model.product.IngredientsModel
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.MediumGray
import com.example.gestok.ui.theme.White
import com.example.gestok.presentation.viewmodel.product.ProductApiViewModel

@Composable
fun IngredientAdd(
    viewModel: ProductApiViewModel,
    onConfirm: (List<IngredientsModel>) -> Unit,
    onCriarNovoIngrediente: () -> Unit,
    onEditarIngrediente: (IngredientsModel) -> Unit
) {

    var selectedIngredients by remember { mutableStateOf(setOf<IngredientsModel>()) }

    val errosIngrediente = viewModel.ingredientesErro
    var ingredientes = viewModel.ingredientes
    val carregando = viewModel.carregouIngredientes

    LaunchedEffect(Unit) {
        viewModel.getIngredientes()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!carregando) Modifier.height(200.dp)
                else if (ingredientes.isEmpty()) Modifier.height(220.dp)
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
                stringResource(R.string.product_ingredients_text),
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
                        Text( stringResource(R.string.loading_product_ingredients), color = MediumGray)
                    }
                }
            }

            ingredientes.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val listaProdutosVazia = viewModel.produtos.isEmpty()

                    Text(
                        if (listaProdutosVazia)
                            "Cadastre pelo menos um produto \n para poder cadastrar ingredientes"
                        else
                            "Nenhum ingrediente cadastrado",
                        color = MediumGray
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp)
                    ) {
                        Button(
                            onClick = { onCriarNovoIngrediente() },
                            enabled = viewModel.produtos.isNotEmpty(),
                            colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(stringResource(R.string.button_product_register_ingredient), color = White)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = { onConfirm(emptyList()) },
                            colors = ButtonDefaults.buttonColors(Blue),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(stringResource(R.string.to_add_text), color = White)
                        }
                    }
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
                            Text(
                                ingrediente.nome,
                                color = Black,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { onEditarIngrediente(ingrediente) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar ingrediente",
                                    tint = LightBlue
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.deletarIngrediente(ingrediente.id) { sucesso ->
                                        if (sucesso) {
                                            viewModel.removerIngredienteLocal(ingrediente.id)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Excluir ingrediente",
                                    tint = Color.Red
                                )
                            }
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
                    onClick = { onCriarNovoIngrediente() },
                    colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text(stringResource(R.string.button_product_register_ingredient), color = White)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { onConfirm(selectedIngredients.toList()) },
                    colors = ButtonDefaults.buttonColors(Blue),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text(stringResource(R.string.to_add_text), color = White)
                }
            }
        }
    }

}





