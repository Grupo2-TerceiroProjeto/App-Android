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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import com.example.gestok.R
import com.example.gestok.components.ExcludeConfirmationDialog
import com.example.gestok.components.productpage.dialogs.NutritionalDataDialog
import com.example.gestok.components.productpage.dialogs.ProductEdit
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray

@Composable
fun ProductData(productData: ProductData){

    var checked by remember { mutableStateOf(true) }
    var showEditDialog by remember { mutableStateOf(false) }
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
                    .align(Alignment.CenterVertically)){
                    Text(
                        text = productData.produto,
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
                        onClick = {showEditDialog = true},
                        modifier = Modifier
                            .height(50.dp)


                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.edicao_f),
                            contentDescription = "Editar",
                        )
                    }

                    IconButton(
                        onClick = {showExcludeConfirmDialog = true},
                        modifier = Modifier
                            .height(50.dp)


                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.trashcan_f),
                            contentDescription = "Excluir",
                        )
                    }

                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Row (Modifier.padding(top = 16.dp)){
                Column (Modifier.padding(end = 30.dp)){
                    Text("Estoque",
                        fontWeight = FontWeight.Bold,
                        color = Blue)

                    Text(text = productData.estoque.toString(),
                        fontWeight = FontWeight.W300,
                        color = Blue)
                }

                Column {
                    Text("Categoria",
                        fontWeight = FontWeight.Bold,
                        color = Blue)

                    Text(text = productData.categoria,
                        fontWeight = FontWeight.W300,
                        color = Blue)
                }

            }

            Row(
                modifier = Modifier.padding(top = 25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){

                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = LightGray,
                        checkedTrackColor = Blue,
                        uncheckedThumbColor = Blue,
                        uncheckedTrackColor = LightGray
                    )
                )

                Text(when(checked){
                    true -> "Disponível"
                    else -> "Não disponível"},
                    color = Blue,
                    fontWeight = W600,
                    modifier = Modifier.padding(start = 16.dp)

                )

            }
        }

    }

    if (showNutritionalDialog) {
        NutritionalDataDialog(
            product = productData.produto,
            ingredients = listOf("Chcolate", "Farinha", "Leite"),
            nutrients = listOf(Triple("Gordura", "10.0", "4.0"), Triple("Proteína", "13.4", "4.3"), Triple("Caloria","90","2.1")),
            onDismissRequest = { showNutritionalDialog = false }
        )
    }


    if(showEditDialog){
        ProductEdit(
            product = productData,
            onDismiss = { showEditDialog = false },
            onConfirm = { newProduto, newEstoque, newCategoria, newValor, newIngredientes ->
                showEditDialog = false
            }
        )
    }

    if(showExcludeConfirmDialog){
        ExcludeConfirmationDialog(
            onDismiss = { showExcludeConfirmDialog = false },
            onConfirm = { showExcludeConfirmDialog = false }
        )
    }

    productData.disponbilidade = checked

}