package com.example.gestok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestok.components.BodyLayoutLogin
import com.example.gestok.screens.Login
import com.example.gestok.screens.internalscreens.InternalScreensNavigation
import com.example.gestok.screens.passwordrecovery.PasswordRecoveryNavigation
import com.example.gestok.ui.theme.GestokTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestokTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            BodyLayoutLogin ("Faça seu login") { Login(navController) }
                        }
                        composable("passwordRecovery") {
                            PasswordRecoveryNavigation(navController)
                        }

                        composable("internalScreens") {
                            InternalScreensNavigation(navController)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun GreetingPreview() {
    GestokTheme {

        val navController = rememberNavController()

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("login") {
                    BodyLayoutLogin ("Faça seu login") { Login(navController) }
                }
                composable("passwordRecovery") {
                    PasswordRecoveryNavigation(navController)
                }

                composable("internalScreens") {
                    InternalScreensNavigation(navController)
                }
            }
        }

    }
}