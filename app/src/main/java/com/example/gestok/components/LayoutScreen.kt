package com.example.gestok.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

val teste: List<String> = listOf("bateria", "argamassa") // Colocar esta variavel no arquivo de OrderContent.kt

@Composable
fun LayoutScreen(
    internalScreensNavController: NavController,
    mainNavController: NavController,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
)
{
    Scaffold(Modifier.background(Color(0xFFF3F3F3)),
        topBar = {
            Topbar(mainNavController)
        },
        bottomBar = {
            BottomNavBar(internalScreensNavController)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}


