package com.example.gestok.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.MediumGray

@Composable
fun Input(description : String) {

    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {Text(description, color = Black, fontWeight = FontWeight.Bold)}

    )

}