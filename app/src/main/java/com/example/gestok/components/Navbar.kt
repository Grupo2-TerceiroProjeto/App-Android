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
import androidx.navigation.NavController
import com.example.gestok.R

data class NavItem(
    val label : String,
    @DrawableRes val iconRes: Int,
    val route: String
)

val navItemList = listOf(
    NavItem("Produtos", R.drawable.produtos, "produtos"),
    NavItem("Config", R.drawable.administracao, "config"),
    NavItem("Dashboard", R.drawable.dashboard, "dashboard"),
    NavItem("Pedidos", R.drawable.pedidos, "pedidos"),
    NavItem("Perfil", R.drawable.perfil, "perfil")
)

@Composable
fun BottomNavBar(
    internalScreensNavController: NavController,
    modifier: Modifier = Modifier,
//    onItemClick: (NavItem) -> Unit
){

    BottomAppBar(
        modifier = modifier
//            .padding(bottom = 49.dp)


//            .clip(RoundedCornerShape(100))
            .height(130.dp)


        ,
        containerColor = Color(0, 91, 164),
        contentColor = Color.White
    ){

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){

            navItemList.forEachIndexed { index, navItem ->
                IconButton(onClick = {internalScreensNavController.navigate(navItem.route)},
                    modifier = Modifier
                        .height(50.dp)
//                        .weight(1f)
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




