package com.example.gestok.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.gestok.ui.theme.Black

@Composable
fun Input(description : String, modifier: Modifier = Modifier) {

    OutlinedTextField(
        modifier = modifier,
        value = "",
        onValueChange = {},
        label = {Text(description, color = Black, fontWeight = FontWeight.Bold)}

    )

}