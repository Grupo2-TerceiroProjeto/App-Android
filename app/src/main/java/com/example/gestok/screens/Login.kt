package com.example.gestok.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestok.components.Input
import com.example.gestok.components.PrimaryButton
import com.example.gestok.ui.theme.LightBlue

@Composable

fun Login(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }


            Column(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Input(
                    "E-mail",
                    Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.Start)
                        .width(300.dp),
                    email,
                    { email = it },
                    keyboardType = KeyboardType.Email
                )

                Input(
                    "Senha",
                    Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.Start)
                        .width(300.dp),
                    senha,
                    {senha = it},
                    visualTransformation = PasswordVisualTransformation()
                )

                Text(
                    text = "Esqueci minha senha",
                    color = LightBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("passwordRecovery")
                    }
                        .padding(top = 12.dp, bottom = 48.dp)
                )

                PrimaryButton("Entrar") { navController.navigate("dashboard") }

            }

}