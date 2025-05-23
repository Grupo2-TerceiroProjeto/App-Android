package com.example.gestok.screens.internalScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.screens.login.UserSession
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.MediumGray
import com.example.gestok.ui.theme.White
import org.koin.compose.koinInject

@Composable
fun Profile(
    modifier: Modifier = Modifier
) {

    val sessaoUsuario = koinInject<UserSession>()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 30.dp)

            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Perfil",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black
                    )

                }
            }
        }

        item{
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                  colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ){

                //--NOME-----------------------------------------
                Text(
                    "Nome",
                    Modifier.padding(start = 20.dp, top = 30.dp),
                    fontWeight = W600,
                    color = Blue
                )

                TextField(
                    value = sessaoUsuario.nome,
                    onValueChange = {},
                    enabled = false,
                    trailingIcon = {
                        Icon(Icons.Filled.Lock, contentDescription = "Campo bloqueado")
                    },
                    colors = TextFieldDefaults.colors(
                       disabledTextColor = MediumGray,
                        disabledContainerColor = LightGray,


                    ),
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20)),
                    singleLine = true
                )


                //--EMAIL -----------------------------------------
                Text(
                    "Email",
                    Modifier.padding(start = 20.dp, top = 30.dp),
                    fontWeight = W600,
                    color = Blue
                )

                TextField(
                    value = sessaoUsuario.login,
                    onValueChange = {},
                    enabled = false,
                    trailingIcon = {
                        Icon(Icons.Filled.Lock, contentDescription = "Campo bloqueado")
                    },
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = MediumGray,
                        disabledContainerColor = LightGray,


                        ),
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20)),
                    singleLine = true
                )


                //--CARGO -----------------------------------------
                Text(
                    "Cargo",
                    Modifier.padding(start = 20.dp, top = 30.dp),
                    fontWeight = W600,
                    color = Blue
                )

                TextField(
                    value = sessaoUsuario.cargo,
                    onValueChange = {},
                    enabled = false,
                    trailingIcon = {
                        Icon(Icons.Filled.Lock, contentDescription = "Campo bloqueado")
                    },
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = MediumGray,
                        disabledContainerColor = LightGray,


                        ),
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20)),
                    singleLine = true
                )

            }
        }
    }

}