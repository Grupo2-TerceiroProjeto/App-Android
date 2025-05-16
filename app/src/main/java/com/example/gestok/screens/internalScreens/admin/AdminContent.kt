package com.example.gestok.screens.internalScreens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.adminpage.RegisterCard
import com.example.gestok.components.adminpage.dialogs.RegisterCreate
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import com.example.gestok.components.adminpage.dialogs.RegisterEdit
import com.example.gestok.screens.internalScreens.admin.data.RegisterData

@Composable
fun AdminContent(modifier: Modifier = Modifier,
                 funcionariosLista: List<RegisterData>,
                 currentPage: MutableState<String>
){

    var showEditRegisterDialog by remember { mutableStateOf(false) }
    var showCreateRegisterDialog by remember { mutableStateOf(false) }


    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
    ){
        item {
            Column (modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp, top = 15.dp)){
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Administração",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black)
                    Button(onClick = {
                        currentPage.value = "registerCreate"
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text("Cadastrar Colaborador", color = White)
                    }
                }

            }

        }


        items(items = funcionariosLista){ funcionario ->

            RegisterCard(
                funcionario,
                funcionariosLista,
                currentPage,
                selectedRegister = remember { mutableStateOf(null) })

        }

    }

    if(showEditRegisterDialog){
        RegisterEdit(funcionario = funcionariosLista.first(),
            onDismiss = { showEditRegisterDialog = false },
            onConfirm = { nome, cargo, email -> showEditRegisterDialog = false })
    }

    if(showCreateRegisterDialog){
        RegisterCreate(
            onDismiss = { showCreateRegisterDialog = false },
            onConfirm = { nome, cargo, email -> showEditRegisterDialog = false })
    }
}
