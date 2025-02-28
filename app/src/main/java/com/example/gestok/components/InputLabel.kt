package com.example.gestok.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.gestok.ui.theme.Black

@Composable
fun InputLabel(description : String) {

    Text(description, fontWeight = FontWeight.Bold, color = Color.Blue)

    OutlinedTextField(
        value = "",
        onValueChange = {}
    )
}