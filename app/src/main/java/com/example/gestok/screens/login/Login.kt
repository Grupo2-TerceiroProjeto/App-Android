package com.example.gestok.screens.login

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestok.MainActivity
import com.example.gestok.components.Input
import com.example.gestok.components.PrimaryButton
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.viewModel.login.LoginApiViewModel

@Composable

fun Login(navController: NavController, viewModel: LoginApiViewModel) {

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val contexto = LocalContext.current

    val erroEmail = viewModel.emailErro
    val erroSenha = viewModel.senhaErro
    val usuarioLogado = viewModel.usuarioLogado

    LaunchedEffect(usuarioLogado) {
        usuarioLogado?.let { user ->
            val mainActivity = Intent(contexto, MainActivity::class.java).apply {
                putExtra("userName", user.nome)
                putExtra("email", user.login)
                putExtra("position", user.cargo)
            }
            contexto.startActivity(mainActivity)
        }
    }

    Column(
                modifier = Modifier
                    .wrapContentHeight()
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
                    keyboardType = KeyboardType.Email,
                    isError = erroEmail != null,
                    supportingText = {
                        erroEmail?.let { Text(text = it) }
                    }

                )

                Input(
                    "Senha",
                    Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.Start)
                        .width(300.dp),
                    senha,
                    {senha = it},
                    visualTransformation = PasswordVisualTransformation(),
                    isError = erroSenha != null,
                    supportingText = {
                        erroSenha?.let { Text(text = it) }
                    }
                )

                Text(
                    text = "Esqueci minha senha",
                    color = LightBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("passwordRecovery")
                    }
                        .padding(top = 12.dp, bottom = 30.dp)
                )

        PrimaryButton("Entrar") {
            viewModel.login(email, senha)
        }

    }

}