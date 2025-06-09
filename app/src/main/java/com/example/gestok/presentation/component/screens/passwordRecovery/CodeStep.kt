package com.example.gestok.presentation.component.screens.passwordRecovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
fun CodeStep(
    navController: NavController,
    viewModel: LoginApiViewModel
) {
    var codigo1 by remember { mutableStateOf("") }
    var codigo2 by remember { mutableStateOf("") }
    var codigo3 by remember { mutableStateOf("") }
    var codigo4 by remember { mutableStateOf("") }
    var codigo5 by remember { mutableStateOf("") }

    val codigoCompleto = "$codigo1$codigo2$codigo3$codigo4$codigo5"

    val focusCodigo1 = remember { FocusRequester() }
    val focusCodigo2 = remember { FocusRequester() }
    val focusCodigo3 = remember { FocusRequester() }
    val focusCodigo4 = remember { FocusRequester() }
    val focusCodigo5 = remember { FocusRequester() }

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
            stringResource(R.string.password_recovery_code_step_msg),
            color = Black,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Input(
                text = "",
                modifier = Modifier
                    .width(48.dp)
                    .focusRequester(focusCodigo1),
                value = codigo1,
                onValueChange = {
                    if (it.length <= 1) {
                        codigo1 = it
                        if (it.length == 1) {
                            focusCodigo2.requestFocus()
                        }
                    }
                },
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                keyboardType = KeyboardType.Number
            )

            Input(
                text = "",
                modifier = Modifier
                    .width(48.dp)
                    .focusRequester(focusCodigo2),
                value = codigo2,
                onValueChange = {
                    if (it.length <= 1) {
                        codigo2 = it
                        if (it.length == 1) {
                            focusCodigo3.requestFocus()
                        }
                    }
                },
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                keyboardType = KeyboardType.Number
            )

            Input(
                text = "",
                modifier = Modifier
                    .width(48.dp)
                    .focusRequester(focusCodigo3),
                value = codigo3,
                onValueChange = {
                    if (it.length <= 1) {
                        codigo3 = it
                        if (it.length == 1) {
                            focusCodigo4.requestFocus()
                        }
                    }
                },
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                keyboardType = KeyboardType.Number
            )

            Input(
                text = "",
                modifier = Modifier
                    .width(48.dp)
                    .focusRequester(focusCodigo4),
                value = codigo4,
                onValueChange = {
                    if (it.length <= 1) {
                        codigo4 = it
                        if (it.length == 1) {
                            focusCodigo5.requestFocus()
                        }
                    }
                },
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                keyboardType = KeyboardType.Number
            )

            Input(
                text = "",
                modifier = Modifier
                    .width(48.dp)
                    .focusRequester(focusCodigo5),
                value = codigo5,
                onValueChange = {
                    if (it.length <= 1) {
                        codigo5 = it
                    }
                },
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                keyboardType = KeyboardType.Number
            )
        }

        if (viewModel.codigoErro != null) {
            Text(
                viewModel.codigoErro!!,
                fontSize = 12.sp,
                color = Color(0xFFD32F2F),
                modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
            )
        }

//        var expirationTime by remember { mutableIntStateOf(60) }
//
//        LaunchedEffect(Unit) {
//            while (expirationTime > 0) {
//                delay(1000L)
//                expirationTime--
//            }
//        }
//
//        Text(
//            text = String.format(Locale.getDefault(), "Expira em %02d:%02d", expirationTime / 60, expirationTime % 60),
//            color = Black,
//            fontSize = 14.sp,
//            modifier = Modifier
//                .padding(top = 16.dp, bottom = 38.dp)
//                .fillMaxWidth()
//        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = if (viewModel.codigoErro == null) 90.dp else 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SecundaryButton(stringResource(R.string.button_code_step_resend_code_text)) {
                val email = viewModel.emailResponse.value?.email
                if (email != null) {
                    viewModel.enviarEmail(email) {}
                }
            }
            PrimaryButton(stringResource(R.string.button_code_step_validate_text)) {
                val recebido = viewModel.emailResponse.value?.codigo
                if (codigoCompleto == recebido) {
                    navController.navigate("newPasswordStep")
                } else {
                    viewModel._codigoErro = "Código inválido"
                }
            }
        }
    }
}
