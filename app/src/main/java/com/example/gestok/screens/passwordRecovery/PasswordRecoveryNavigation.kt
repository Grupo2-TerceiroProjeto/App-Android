package com.example.gestok.screens.passwordRecovery

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestok.components.BodyLayoutLogin
import com.example.gestok.viewModel.login.LoginApiViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PasswordRecoveryNavigation(mainNavController: NavController) {
    val recoveryNavController = rememberNavController()
    val viewModel: LoginApiViewModel = koinViewModel()

    NavHost(navController = recoveryNavController, startDestination = "emailStep") {
        composable("emailStep") { BodyLayoutLogin ("Recuperar senha") { EmailStep(recoveryNavController, mainNavController, viewModel) } }
        composable("codeStep") { BodyLayoutLogin ("Recuperar senha") {CodeStep(recoveryNavController, viewModel)} }
        composable("newPasswordStep") { BodyLayoutLogin ("Recuperar senha") {NewPasswordStep(mainNavController, viewModel)} }
    }
}