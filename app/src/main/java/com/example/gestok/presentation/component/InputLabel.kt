package com.example.gestok.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray

@Composable
fun InputLabel(
    text: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    erro: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    maxLength: Int

) {
    Column{

        Text(text, fontWeight = W600, color = Blue)

        TextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                focusedTextColor = Black,
                unfocusedContainerColor = LightGray,
                unfocusedTextColor = Black
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            readOnly = readOnly,
            isError = erro != null,
            supportingText = {
                erro?.let { Text(text = it) }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20))

        )
    }

}