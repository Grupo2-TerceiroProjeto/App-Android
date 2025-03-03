package com.example.gestok.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gestok.R
import com.example.gestok.components.Input
import com.example.gestok.components.PrimaryButton
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue

@Composable

fun Login(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(575.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(413.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White, shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Faça seu  Login",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 50.dp))

                Input("E-mail", Modifier.padding(top = 12.dp))
                Input("Senha", Modifier.padding(top = 12.dp))
                Text(
                    text = "Esqueci minha senha",
                    color = LightBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("recuperarSenha")
                    }
                        .padding(top = 12.dp, bottom = 48.dp)
                )
                PrimaryButton("Entrar")

            }
        }
    }

}