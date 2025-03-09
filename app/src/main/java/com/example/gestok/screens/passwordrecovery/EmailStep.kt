package com.example.gestok.screens.passwordrecovery

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestok.components.Input
import com.example.gestok.components.PrimaryButton
import com.example.gestok.components.SecundaryButton
import com.example.gestok.ui.theme.Black

@Composable
fun EmailStep(navController: NavController, mainNavController: NavController) {

        var email by remember { mutableStateOf("") }

        Column (
            modifier = Modifier
                .height(300.dp)
                .width(300.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                "Insira o seu E-mail para receber o código de validação",
                color = Black,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 5.dp))

            Input(
                description = "Email",
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 80.dp)
                    .align(Alignment.Start)
                    .width(300.dp),
                value = email,
                onValueChange = { email = it }
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SecundaryButton("Voltar") { mainNavController.navigate("login") }
                Spacer(modifier = Modifier.width(24.dp))
                PrimaryButton("Enviar Código") { navController.navigate("codeStep") }
            }

        }

}