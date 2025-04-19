package com.example.gestok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestok.components.LayoutScreen
import com.example.gestok.ui.theme.GestokTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userName = intent.getStringExtra("userName")
        val email = intent.getStringExtra("email")
        val position = intent.getStringExtra("position")

        enableEdgeToEdge()
        setContent {
            GestokTheme {
                LayoutScreen(this, userName!!, email!!, position!!)
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun GreetingPreview() {

}