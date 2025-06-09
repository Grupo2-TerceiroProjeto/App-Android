package com.example.gestok.presentation.component.screens.passwordRecovery

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestok.R
import com.example.gestok.presentation.component.BodyLayoutLogin
import com.example.gestok.presentation.viewmodel.login.LoginApiViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PasswordRecoveryNavigation(mainNavController: NavController) {
    val recoveryNavController = rememberNavController()
    val viewModel: LoginApiViewModel = koinViewModel()

    NavHost(navController = recoveryNavController, startDestination = "emailStep") {
        composable("emailStep") {
            BodyLayoutLogin(stringResource(R.string.title_password_recovery)) {
                EmailStep(
                    recoveryNavController,
                    mainNavController,
                    viewModel
                )
            }
        }
        composable("codeStep") {
            BodyLayoutLogin(stringResource(R.string.title_password_recovery)) {
                CodeStep(
                    recoveryNavController,
                    viewModel
                )
            }
        }
        composable("newPasswordStep") {
            BodyLayoutLogin(stringResource(R.string.title_password_recovery)) {
                NewPasswordStep(
                    mainNavController,
                    viewModel
                )
            }
        }
    }
}