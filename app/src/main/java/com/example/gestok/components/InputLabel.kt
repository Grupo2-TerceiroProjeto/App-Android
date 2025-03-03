package com.example.gestok.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.gestok.ui.theme.Blue

@Composable
fun InputLabel(description : String) {

    Text(description, fontWeight = FontWeight.Bold, color = Blue)

    OutlinedTextField(
        value = "",
        onValueChange = {}
    )
}