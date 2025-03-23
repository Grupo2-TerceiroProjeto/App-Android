package com.example.gestok.components.productpage.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gestok.R
import com.example.gestok.components.productpage.ProductData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.LightGray

@Composable
fun IngredientBlock(
    produto: ProductData
) {

    produto.ingredientes.forEach { ingredient ->

      Row (Modifier
          .fillMaxWidth(),

          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween){


            Row(
                Modifier
                    .clip(shape = RoundedCornerShape(15))
                    .background(LightGray)
                    .padding(10.dp)
                    .weight(0.5F),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(text = ingredient.nome + " " + "(" + ingredient.unidade + ")", color = Black)

                TextField(
                    value = ingredient.quantidade.toString(),
                    onValueChange = {},
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray, // Cor de fundo quando focado
                        unfocusedContainerColor = Color.LightGray,   // Cor de fundo quando não focado
                        focusedTextColor = Black,          // Cor do texto quando focado
                        unfocusedTextColor = Black,         // Cor do texto quando não focado
                        focusedIndicatorColor = Color.LightGray, // Cor do indicador (borda) quando focado
                        unfocusedIndicatorColor = Color.LightGray,

                        ),  maxLines = 1,
                    modifier = Modifier
                        .width(74.dp)
                        .clip(shape = RoundedCornerShape(15))

                )


            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .height(50.dp)


            ) {
                Image(
                    painter = painterResource(id = R.drawable.edicao_f),
                    contentDescription = "Editar",
                    )


            }

        }
            Spacer(modifier = Modifier.height(15.dp))



    }

}

