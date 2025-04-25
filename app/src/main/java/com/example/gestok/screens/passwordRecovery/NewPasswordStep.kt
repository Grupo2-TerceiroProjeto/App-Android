package com.example.gestok.screens.passwordRecovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestok.components.Input
import com.example.gestok.components.PrimaryButton

@Composable
fun NewPasswordStep(navController: NavController) {

    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Input(
            "Nova senha",
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
            visualTransformation = PasswordVisualTransformation()
        )
        Input(
            "Confirmar senha",
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
            visualTransformation = PasswordVisualTransformation())

        PrimaryButton ("Concluir") { navController.navigate("login") }

    }

}