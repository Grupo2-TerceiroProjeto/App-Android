package com.example.gestok.components

import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gestok.R

data class NavItem(
    val label : String,
    @DrawableRes val iconRes: Int
)

val navItemList = listOf(
    NavItem("Produtos", R.drawable.produtos),
    NavItem("Config", R.drawable.administracao),
    NavItem("Dashboard", R.drawable.dashboard),
    NavItem("Pedidos", R.drawable.pedidos),
    NavItem("Perfil", R.drawable.perfil),
)

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    onItemClick: (NavItem) -> Unit
){

    BottomAppBar(
        modifier = modifier
            .padding(top = 40.dp,
                    bottom = 49.dp,
                    start = 40.dp,
                    end = 40.dp)
            .clip(RoundedCornerShape(100))
            .height(80.dp)


        ,
        containerColor = Color(0, 91, 164),
        contentColor = Color.White
    ){

        Row(modifier = Modifier.offset(y = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            navItemList.forEachIndexed { index, navItem ->
                IconButton(onClick = {},
                    modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
//                        .align(Alignment.CenterVertically)

                ) {
                    Image(
                        painter = painterResource(id = navItem.iconRes),
                        contentDescription = navItem.label,
                        modifier = Modifier.size(32.dp)
                    )

                }
            }
        }

    }
}




