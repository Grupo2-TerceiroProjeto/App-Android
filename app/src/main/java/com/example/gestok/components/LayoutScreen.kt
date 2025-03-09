package com.example.gestok.components

//import android.graphics.drawable.Icon

//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.gestok.components.orderscreen.OrderContent

val teste: List<String> = listOf("bateria", "argamassa")

@Composable
fun LayoutScreen(modifier: Modifier = Modifier) {
    Scaffold(Modifier.background(Color(0xFFF3F3F3)),
        topBar = {
            Topbar() {}
        },
        bottomBar = {
            BottomNavBar() {}
        }
    ) { innerPadding ->
        OrderContent(modifier = Modifier.padding(innerPadding))
    }
}


