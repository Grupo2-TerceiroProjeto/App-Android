package com.example.gestok.screens.internalscreens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestok.components.LayoutScreen

@Composable
fun InternalScreensNavigation(mainNavController: NavController) {

    val internalScreensNavController = rememberNavController()

    NavHost(navController = internalScreensNavController, startDestination = "dashboard") {
        composable("dashboard") { LayoutScreen(internalScreensNavController, mainNavController){ Dashboard() } }
        composable("perfil") { LayoutScreen (internalScreensNavController, mainNavController){ Profile() } }
    }

}