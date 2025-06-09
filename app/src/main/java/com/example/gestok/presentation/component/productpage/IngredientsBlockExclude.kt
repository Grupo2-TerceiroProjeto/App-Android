package com.example.gestok.presentation.component.productpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gestok.domain.model.product.IngredientsBlock
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.LightGray

@Composable
fun IngredientBlockExclude (
    ingredientes: List<IngredientsBlock>,
    updateQuantidade: (Int, Int) -> Unit,
    onExcluirClick: () -> Unit
) {

    ingredientes.forEachIndexed { index, item ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier
                    .clip(shape = RoundedCornerShape(15))
                    .weight(1f)
                    .background(LightGray)
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.nome,
                    color = Black,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                TextField(
                    value = item.quantidade.toString(),
                    onValueChange = {
                        val newQuantidade = it.toIntOrNull() ?: 0
                        updateQuantidade(index, newQuantidade)
                    },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Black,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        focusedTextColor = Black,
                        unfocusedTextColor = Black,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,

                        ),
                    maxLines = 1,
                    modifier = Modifier
                        .width(73.dp)
                        .clip(shape = RoundedCornerShape(15))

                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { onExcluirClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

        }

    }

}