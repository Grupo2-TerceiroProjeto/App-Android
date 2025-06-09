package com.example.gestok.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestok.R
import com.example.gestok.presentation.component.BodyLayoutLogin
import com.example.gestok.presentation.component.screens.login.Login
import com.example.gestok.presentation.component.screens.passwordRecovery.PasswordRecoveryNavigation
import com.example.gestok.ui.theme.GestokTheme
import com.example.gestok.presentation.viewmodel.login.LoginApiViewModel
import org.koin.androidx.compose.koinViewModel

class AuthActivity : ComponentActivity() {
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
                            val viewModel: LoginApiViewModel = koinViewModel()
                            BodyLayoutLogin (stringResource(R.string.title_login)) { Login(navController, viewModel) }
                        }
                        composable("passwordRecovery") {
                            PasswordRecoveryNavigation(navController)
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    GestokTheme {
        val navController = rememberNavController()

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("login") {
                    val viewModel: LoginApiViewModel = koinViewModel()
                    BodyLayoutLogin ("Faça seu login") { Login(navController, viewModel) }
                }
                composable("passwordRecovery") {
                    PasswordRecoveryNavigation(navController)
                }
            }
        }
    }
}