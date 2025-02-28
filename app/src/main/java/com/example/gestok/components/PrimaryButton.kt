package com.example.gestok.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.White

@Composable
fun PrimaryButton(description : String) {

    Button(onClick = {},
        modifier = Modifier
            .width(141.dp)
            .height(39.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Blue),
        shape = RoundedCornerShape(18.dp)
    ) {


        Text(
            text = description,
            color = White,
            fontWeight = FontWeight.Bold
        )
    }

}