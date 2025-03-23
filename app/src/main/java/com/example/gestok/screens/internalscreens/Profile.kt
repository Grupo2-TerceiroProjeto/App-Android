package com.example.gestok.screens.internalscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.components.InputLabel
import com.example.gestok.ui.theme.Black

@Composable
fun Profile() {

    var nome by remember { mutableStateOf("Jessica") }
    var email by remember { mutableStateOf("jessicaDoces&Salgados@gmail.com") }
    var cargo by remember { mutableStateOf("Administrador") }

   Column(modifier = Modifier
       .fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       ) {

       Text("Perfil",
           color = Black,
           fontWeight = FontWeight.Bold,
           fontSize = 20.sp,
           modifier = Modifier
               .align(Alignment.Start)
               .padding(start = 30.dp, top = 28.dp))

       Spacer(modifier = Modifier.height(16.dp))

      Box(modifier = Modifier
          .background(Color.White)
          .height(436.dp)
          .width(350.dp)
          .clip(RoundedCornerShape(7.dp))) {

          Column(
              modifier = Modifier
                  .width(340.dp)
                  .fillMaxHeight()
                  .padding(30.dp)
          ) {

              InputLabel(
                  "Nome",
                  value = nome,
                  enabled = false,
                  trailingIcon = {
                      Icon(
                          painter = painterResource(id = R.drawable.bloqueado),
                          contentDescription = "Blocked"
                      )
                  })

              InputLabel(
                  "Email",
                  modifierLabel = Modifier
                      .padding(top = 20.dp),
                  value = email,
                  enabled = false,
                  trailingIcon = {
                      Icon(
                          painter = painterResource(id = R.drawable.bloqueado),
                          contentDescription = "Blocked"
                      )
                  })

              InputLabel(
                  "Cargo",
                  Modifier.padding(top = 20.dp),
                  value = cargo,
                  enabled = false,
                  trailingIcon = {
                      Icon(
                          painter = painterResource(id = R.drawable.bloqueado),
                          contentDescription = "Blocked"
                      )
                  })

          }

      }
   }

}