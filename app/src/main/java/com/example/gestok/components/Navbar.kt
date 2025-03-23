import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.annotation.DrawableRes
import com.example.gestok.R
import com.example.gestok.ui.theme.LightBlue

data class NavItem(
    val label: String,
    @DrawableRes val iconRes: Int,
    val screen: String
)

val navItemList = listOf(
    NavItem("Produtos", R.drawable.produtos, "produtos"),
    NavItem("Config", R.drawable.administracao, "config"),
    NavItem("Dashboard", R.drawable.dashboard, "dashboard"),
    NavItem("Pedidos", R.drawable.pedidos, "pedidos"),
    NavItem("Perfil", R.drawable.perfil, "perfil"),
)

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    onItemClick: (NavItem) -> Unit
) {
    var selectedNavItem by remember { mutableStateOf(navItemList.first()) }
    BottomAppBar(
        modifier = modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .height(100.dp),
        containerColor = Color(0, 91, 164),
        contentColor = Color.White
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            navItemList.forEachIndexed { index, navItem ->
                IconButton(
                    onClick = {
                        selectedNavItem = navItem
                        onItemClick(navItem)
                    },
                    modifier = Modifier.height(50.dp)
                ) {
                    Image(
                        painter = painterResource(id = navItem.iconRes),
                        contentDescription = navItem.label,
                        modifier = Modifier.size(32.dp),
                        colorFilter = if (selectedNavItem == navItem) {
                            ColorFilter.tint(LightBlue)
                        } else {
                            null
                        }
                    )

                }
            }
        }

    }
}




