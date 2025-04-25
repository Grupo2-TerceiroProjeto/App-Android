package com.example.gestok.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue

@Composable
fun InputLabel(
    description : String,
    modifierLabel: Modifier = Modifier,
    modifier: Modifier,
    value : String,
    onValueChange: (String) -> Unit = {},
    singleLine : Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    trailingIcon: (@Composable (() -> Unit))? = null

) {

    Text(description, fontWeight = FontWeight.Bold, color = Blue, modifier = modifierLabel)

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = Black),
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        enabled = enabled,
        trailingIcon = trailingIcon,
        modifier = modifier
    )
}