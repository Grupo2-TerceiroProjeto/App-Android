package com.example.gestok.screens.internalScreens.admin

import SelectOption
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.components.InputLabel
import com.example.gestok.screens.internalScreens.admin.data.RegisterCreateData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.admin.AdminApiViewModel


@Composable
fun RegisterCreate(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSucess: () -> Unit,
    viewModel: AdminApiViewModel
    ){

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("Selecione uma opção") }
    var senha by remember { mutableStateOf("") }

    val novoFuncionario = RegisterCreateData(
        nome = nome,
        login = email,
        senha = senha,
        cargo = cargo,
        idEmpresa = 0
    )

    LaunchedEffect(Unit) {
        viewModel.limparErrosFormulario()
    }

    LazyColumn (  modifier = modifier
        .fillMaxSize()
        .background(White),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF005BA4), shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }

                    Text(
                        stringResource(R.string.administration_register_employee_text),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    Column {
                        InputLabel(
                            text = stringResource(R.string.label_name),
                            value = nome,
                            onValueChange = {
                                val filtered = it.filter { char -> char.isLetter() || char.isWhitespace() }
                                nome = filtered
                            },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                            erro = viewModel.nomeErro,
                            maxLength = 30
                        )
                    }

                    Column {
                        InputLabel(
                            text = stringResource(R.string.label_email),
                            value = email,
                            onValueChange = { email = (it) },
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                            erro = viewModel.emailErro,
                            maxLength = 45
                        )
                    }

                    Column {
                        SelectOption(
                            text = stringResource(R.string.label_position),
                            value = cargo,
                            onValueChange = { cargo = it },
                            list = listOf(
                                "ADMIN",
                                "SUPERVISOR",
                                "COLABORADOR"
                            ),
                            erro = viewModel.cargoErro
                        )
                    }

                    Column {
                        InputLabel(
                            text =  stringResource(R.string.label_password),
                            value = senha,
                            onValueChange = {
                               senha = (it)
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Password,
                            erro = viewModel.senhaErro,
                            maxLength = 300
                        )
                    }

                }

            }

        }

        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            viewModel.salvarFuncionario(
                                novoFuncionario,
                                onBack,
                                onSucess
                            )
                        },
                        colors = ButtonDefaults.buttonColors(Blue),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.button_save_text), color = White, fontSize = 16.sp)
                    }

                    if (viewModel.cadastroErro != null) {
                        Text(
                            viewModel.cadastroErro!!,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }

                }
            }
        }

    }
}