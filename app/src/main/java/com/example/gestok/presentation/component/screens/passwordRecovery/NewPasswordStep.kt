package com.example.gestok.presentation.component.screens.passwordRecovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestok.R
import com.example.gestok.presentation.component.Input
import com.example.gestok.presentation.component.PrimaryButton
import com.example.gestok.domain.model.auth.UserStepReset
import com.example.gestok.presentation.viewmodel.login.LoginApiViewModel

@Composable
fun NewPasswordStep(
    navController: NavController,
    viewModel: LoginApiViewModel
) {

    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.limparErros()
    }

    Column(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Input(
            stringResource(R.string.label_new_password),
            Modifier
                .padding(top = 18.dp)
                .align(Alignment.Start)
                .width(300.dp),
            senha,
            onValueChange = {
                if (it.length <= 45) {
                    senha = it
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.senhaErro != null,
            supportingText = {
                viewModel.senhaErro?.let { Text(text = it) }
            }
        )
        Input(
            stringResource(R.string.label_confirm_password),
            Modifier
                .padding(top = 18.dp, bottom = 55.dp)
                .align(Alignment.Start)
                .width(300.dp),
            confirmarSenha,
            onValueChange = {
                if (it.length <= 45) {
                    confirmarSenha = it
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.confirmarSenhaErro != null,
            supportingText = {
                viewModel.confirmarSenhaErro?.let { Text(text = it) }
            }
        )

        PrimaryButton(stringResource(R.string.button_new_password_step_conclude_text)) {
            val email = viewModel.emailResponse.value?.email ?: return@PrimaryButton

            viewModel.redefinirSenha(
                UserStepReset(email, senha, confirmarSenha)
            ) {
                navController.navigate("login")
            }
        }

    }

}