import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray

@Composable
fun SelectOption(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    list: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        text,
        fontWeight = W600,
        color = Blue
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightGray,
                unfocusedContainerColor = LightGray,
                focusedTextColor = Black,
                unfocusedTextColor = Black,
                focusedIndicatorColor = LightGray,
                unfocusedIndicatorColor = LightGray
            ),
            modifier = Modifier
                .padding(end = 10.dp)
                .width(330.dp)
                .clip(RoundedCornerShape(20)),

            singleLine = true
        )

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = Blue,

            modifier = Modifier
                .clickable { expanded = !expanded }
                .scale(2.0F, 2.0F)


        )
    }

    Box {

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(330.dp)


        ) {
           list.forEach { statusOption ->
                DropdownMenuItem(
                    text = { Text(statusOption) },
                    onClick = {
                        onValueChange(statusOption)
                        expanded = false
                    }
                )
            }
        }

    }
}


