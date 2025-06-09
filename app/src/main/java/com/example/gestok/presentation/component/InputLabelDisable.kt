package com.example.gestok.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.MediumGray

@Composable
fun InputLabelDisable(
    modifierText: Modifier = Modifier,
    modifierInput: Modifier = Modifier,
    text:String,
    value: String,
    singleLine: Boolean = true,
    icon: Boolean = true,
) {

    Text(
        text,
        modifier = modifierText,
        fontWeight = W600,
        color = Blue
    )

    if(text !== "") {
        Spacer(modifier = Modifier.height(8.dp))
    }

    TextField(
        value = value,
        onValueChange = {},
        enabled = false,
        trailingIcon = if (icon) {
            {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Campo bloqueado"
                )
            }
        } else null,
        colors = TextFieldDefaults.colors(
            disabledTextColor = MediumGray,
            disabledContainerColor = LightGray,


            ),
        modifier = modifierInput
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20)),
        singleLine = singleLine
    )

}