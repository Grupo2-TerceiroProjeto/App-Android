package com.example.gestok.components.orderpage.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gestok.components.teste

import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.LightGray

@Composable
fun PedidoBlock(
    itens: List<String> = teste,
) {

    itens.forEach { item ->

        Row(
            Modifier
                .clip(shape = RoundedCornerShape(15))
               .background(LightGray)
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text("$item", color = Black)
            TextField(
                value = "2000",
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
            Spacer(modifier = Modifier.height(15.dp))

    }


}


