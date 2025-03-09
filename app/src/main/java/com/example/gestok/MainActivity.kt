package com.example.gestok

import SelectOption
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestok.components.Input
import com.example.gestok.components.InputLabel
import com.example.gestok.ui.theme.GestokTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestokTheme {
                    LayoutScreen()
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true,
    device = "spec:parent=pixel_2,navigation=buttons", backgroundColor = 0xFF3F3F3F
)
@Composable
fun GreetingPreview() {
    GestokTheme {
        LayoutScreen()
    }
}