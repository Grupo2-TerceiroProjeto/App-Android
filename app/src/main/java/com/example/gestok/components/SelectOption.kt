import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R

@Composable
fun SelectOption(description: String, list: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(list.firstOrNull() ?: "") }

    Text(text = description, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Blue)

    Box {

        Row(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                .clickable { expanded = true }
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .height(45.dp)
                .width(263.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = selectedOption, modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.seta), contentDescription = "Dropdown arrow", tint = Color.Blue)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(280.dp)
        ) {
            list.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                    }
                )
            }
        }
    }
}


