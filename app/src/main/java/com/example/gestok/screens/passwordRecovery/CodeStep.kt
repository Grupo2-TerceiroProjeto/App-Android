package com.example.gestok.screens.passwordRecovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestok.components.Input
import com.example.gestok.components.PrimaryButton
import com.example.gestok.components.SecundaryButton
import com.example.gestok.ui.theme.Black

@Composable
fun CodeStep(navController: NavController) {
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

    Column(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "O código de verificação foi enviado para seu E-mail",
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
                    if (it.length == 1) {
                        codigo1 = it
                        focusCodigo2.requestFocus()
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
                    if (it.length == 1) {
                        codigo2 = it
                        focusCodigo3.requestFocus()
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
                    if (it.length == 1) {
                        codigo3 = it
                        focusCodigo4.requestFocus()
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
                    if (it.length == 1) {
                        codigo4 = it
                        focusCodigo5.requestFocus()
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
                    if (it.length == 1) {
                        codigo5 = it
                    }
                },
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                keyboardType = KeyboardType.Number
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
                .padding(top = 78.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SecundaryButton("Reenviar código") { }
            PrimaryButton("Validar") { navController.navigate("newPasswordStep") }
        }
    }
}
