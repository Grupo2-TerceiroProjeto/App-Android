package com.example.gestok.screens.internalScreens.admin

import SelectOption
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.InputLabel
import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.DarkBlue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White

@Composable
fun RegisterEdit(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    funcionario: RegisterData
){

    var editedNome by remember { mutableStateOf(funcionario.nome) }
    var editedCargo by remember { mutableStateOf(funcionario.cargo) }
    var editedEmail by remember { mutableStateOf(funcionario.email) }

    var showCancelConfirmDialog by remember { mutableStateOf(false) }

    LazyColumn (  modifier = modifier
        .fillMaxSize()
        .background(White),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){

        // -- HEADER -------------------------
        item {

            Row(
                Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = DarkBlue, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }

                Text(
                    "Cadastrar Funcionário",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Black,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                )
            }

        }

        //--- NOME COLABORADOR -------------------------------------------
        item {
            InputLabel(
                text = "Nome",
                value = editedNome,
                onValueChange = {
                    val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                    editedNome =  filtered},
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                maxLength = 45
            )
        }

        //--- EMAIL COLABORADOR -------------------------------------------
        item {
            InputLabel(
                text = "Email",
                value = editedEmail,
                onValueChange = { editedEmail = it },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                maxLength = 45
            )
        }

        //--- CARGO COLABORADOR -------------------------------------------
        item {
            SelectOption(
                text = "Cargo",
                value = editedCargo,
                onValueChange = { editedCargo = it },
                list = listOf(
                    "Cozinheiro(a)",
                    "Gerente",
                    "Atendente",
                    "Administrador(a)"
                )
            )
        }

        //--- BOTÃO CONFIRMAR -------------------------------------------
        item {
            Row(
                Modifier.fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.End

            ) {

                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(LightBlue)
                ) {
                    Icon(imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = White
                    )
                    Text("Concluir", color = White)
                }
            }
        }


    }
}