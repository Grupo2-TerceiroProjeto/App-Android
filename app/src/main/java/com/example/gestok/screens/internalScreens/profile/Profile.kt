package com.example.gestok.screens.internalScreens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.components.InputLabelDisable
import com.example.gestok.screens.login.data.UserSession
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import org.koin.compose.koinInject

@Composable
fun Profile(
    modifier: Modifier = Modifier
) {

    val sessaoUsuario = koinInject<UserSession>()

    LazyColumn(
        modifier = modifier
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
                        stringResource(R.string.title_profile),
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
                InputLabelDisable(
                    modifierText = Modifier.padding(start = 20.dp, top = 30.dp),
                    modifierInput = Modifier.padding(horizontal = 15.dp),
                    text =  stringResource(R.string.label_name),
                    value = sessaoUsuario.nome
                )

                //--EMAIL -----------------------------------------
                InputLabelDisable(
                    modifierText = Modifier.padding(start = 20.dp, top = 30.dp),
                    modifierInput = Modifier.padding(horizontal = 15.dp),
                    text = stringResource(R.string.label_email),
                    value = sessaoUsuario.login
                )


                //--CARGO -----------------------------------------
                InputLabelDisable(
                    modifierText = Modifier.padding(start = 20.dp,top = 30.dp),
                    modifierInput = Modifier.padding(horizontal = 15.dp).padding(bottom = 30.dp),
                    text =  stringResource(R.string.label_position),
                    value = sessaoUsuario.cargo
                )

            }
        }
    }

}