package com.example.gestok.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray

@Composable
fun NutritionalDataDialog(
    product : String,
    ingredients : List<String>,
    nutrients: List<Triple<String, String, String>>,
    onDismissRequest: () -> Unit
) {

    Dialog(onDismissRequest = { onDismissRequest() }){

        Card(
            modifier = Modifier
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White)
        ){

            Column(modifier = Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "Info - $product",
                        modifier = Modifier
                            .weight(0.6f),
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

                Spacer(modifier = Modifier
                    .height(20.dp))

                InputLabel(
                    description = "Ingredientes",
                    value = ingredients.joinToString(", "),
                    singleLine = false,
                    enabled = false,
                    modifier = Modifier
                        .background(LightGray)
                )

                Spacer(modifier = Modifier
                    .height(20.dp))

                Text(
                    "Tabela Nutricional",
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    fontSize = 14.sp)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End) {
                            Text("100g", color = LightBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(end = 45.dp))
                            Text("%VD", color = LightBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Divider(modifier = Modifier.padding(vertical = 4.dp))

                        nutrients.forEach { (name, quantity, dailyValue) ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    name,
                                    color = LightBlue,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold)
                                Text(
                                    quantity,
                                    fontSize = 14.sp)
                                Text(
                                    dailyValue,
                                    fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}