package com.example.gestok.screens.internalScreens.admin

import SkeletonLoader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.adminpage.RegisterCard
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import com.example.gestok.screens.internalScreens.admin.data.RegisterData
import com.example.gestok.viewModel.admin.AdminApiViewModel

@Composable
fun AdminContent(
    modifier: Modifier = Modifier,
    viewModel: AdminApiViewModel,
    currentPage: MutableState<String>,
    selectedRegister: MutableState<RegisterData?>
){

    val erroFuncionarios = viewModel.funcionariosErro
    val funcionarios = viewModel.funcionarios
    val carregando = viewModel.carregouFuncionarios

    LaunchedEffect(Unit) {
        viewModel.getFuncionarios()
    }

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
    ){
        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)){
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Administração",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black)
                    Button(
                        onClick = {currentPage.value = "createRegister"},
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text("Cadastrar Colaborador", color = White)
                    }
                }

                if (erroFuncionarios != null) {
                    Text(
                        erroFuncionarios,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                    )
                }

            }

        }


        item {
            when {
                !carregando -> {
                    repeat(4) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )
                        ) {
                            SkeletonLoader(modifier = Modifier
                                .fillMaxWidth()
                                .height(235.dp),
                            )
                        }
                    }
                }

                funcionarios.isEmpty() -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 250.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Nenhum funcionário cadastrado",
                            fontSize = 16.sp,
                            color = Black
                        )
                    }
                }

                else -> {

                    funcionarios.forEach { funcionario ->
                        RegisterCard(
                            funcionario = funcionario,
                            currentPage = currentPage,
                            selectedRegister = selectedRegister,
                            viewModel = viewModel
                        )
                    }

                }
            }
        }

    }
}
