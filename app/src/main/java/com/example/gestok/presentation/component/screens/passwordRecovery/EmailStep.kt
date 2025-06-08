package com.example.gestok.presentation.component.screens.passwordRecovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestok.R
import com.example.gestok.presentation.component.Input
import com.example.gestok.presentation.component.PrimaryButton
import com.example.gestok.presentation.component.SecundaryButton
import com.example.gestok.ui.theme.Black
import com.example.gestok.presentation.viewmodel.login.LoginApiViewModel

@Composable
fun EmailStep(
    navController: NavController,
    mainNavController: NavController,
    viewModel: LoginApiViewModel
) {

    var email by remember { mutableStateOf("") }

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

        Text(
            stringResource(R.string.password_recovery_email_step_msg),
            color = Black,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 5.dp)
        )

        Input(
            text = stringResource(R.string.label_email),
            modifier = Modifier
                .padding(top = 24.dp,  bottom = if (viewModel.emailNaoEncontrado == null) 80.dp else 5.dp)
                .align(Alignment.Start)
                .width(300.dp),
            value = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email,
            isError = viewModel.emailErro != null,
            supportingText = {
                viewModel.emailErro?.let { Text(text = it) }
            }
        )

        if (viewModel.emailNaoEncontrado != null) {
            Text(
                viewModel.emailNaoEncontrado!!,
                fontSize = 12.sp,
                color = Color(0xFF196BAD),
                modifier = Modifier
                    .padding(bottom = 22.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SecundaryButton(stringResource(R.string.button_back_text)) { mainNavController.navigate("login") }
                Spacer(modifier = Modifier.width(24.dp))
                PrimaryButton(stringResource(R.string.button_email_step_send_code_text)) {
                    viewModel.enviarEmail(email) {
                        navController.navigate("codeStep")
                    }
                }
            }
        }

    }

}