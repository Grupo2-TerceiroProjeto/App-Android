package com.example.gestok.components

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.AuthActivity
import com.example.gestok.R
import com.example.gestok.screens.login.UserSession
import org.koin.compose.koinInject

@Composable
fun Topbar(
    activity: Activity
){
    val sessaoUsuario = koinInject<UserSession>()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0, 91, 164))

            .padding(
                top = 37.dp,
                bottom = 10.dp,
                start = 23.dp,
                end = 23.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween


    ){
        Text (text = "Olá, ${sessaoUsuario.nome}",
            color = Color.White,
            fontSize = 20.sp,

        )

        IconButton(onClick = {
            val context = activity
            val intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                modifier = Modifier.size(25.dp)
            )
        }

    }
}