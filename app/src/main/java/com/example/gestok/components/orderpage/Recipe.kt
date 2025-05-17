package com.example.gestok.components.orderpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gestok.screens.internalScreens.order.data.IngredientsFormat
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.White

@Composable
fun Recipe(
    onDismiss: () -> Unit,
    ingredientes: List<IngredientsFormat>

) {

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ), elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {

            Row(
                Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Lista de Ingredintes",
                    fontWeight = W600,
                    color = Blue,
                    fontSize = 20.sp
                )
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(Blue)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = White
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 190.dp)
            ) {

                item{

                    ingredientes.forEach{ ingrediente ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp, horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = ingrediente.nome)
                        Text(text = "${ingrediente.quantidade} ${ingrediente.medida}")
                    }
                }
            }

            }

        }

    }

}