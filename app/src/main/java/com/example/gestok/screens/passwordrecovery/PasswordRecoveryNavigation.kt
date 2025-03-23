package com.example.gestok.screens.passwordrecovery

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestok.components.BodyLayoutLogin

@Composable
fun PasswordRecoveryNavigation(mainNavController: NavController) {
    val recoveryNavController = rememberNavController()

    NavHost(navController = recoveryNavController, startDestination = "emailStep") {
        composable("emailStep") { BodyLayoutLogin ("Recuperar senha") { EmailStep(recoveryNavController, mainNavController) } }
        composable("codeStep") { BodyLayoutLogin ("Recuperar senha") {CodeStep(recoveryNavController)} }
        composable("newPasswordStep") { BodyLayoutLogin ("Recuperar senha") {NewPasswordStep(mainNavController)} }
    }
}