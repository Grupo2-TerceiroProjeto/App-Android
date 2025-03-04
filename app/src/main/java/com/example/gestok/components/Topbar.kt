package com.example.gestok.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.gestok.R

@Composable
fun Topbar(
    modifier: Modifier = Modifier,
    onItemClick: (NavItem) -> Unit
){
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


    ){
        Text (text = "Olá, pessoa",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(end = 210.dp)
        )

        IconButton(onClick = {},
            modifier = Modifier
                .height(50.dp)
                .weight(1f)


        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                modifier = Modifier.size(25.dp)
            )

        }

    }
}