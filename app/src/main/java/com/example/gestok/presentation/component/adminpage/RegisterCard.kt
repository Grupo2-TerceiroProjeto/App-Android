package com.example.gestok.presentation.component.adminpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gestok.R
import com.example.gestok.presentation.component.ExcludeConfirmationDialog
import com.example.gestok.domain.model.admin.RegisterModel
import com.example.gestok.ui.theme.Blue
import com.example.gestok.presentation.viewmodel.admin.AdminApiViewModel

@Composable
fun RegisterCard(
    funcionario: RegisterModel,
    currentPage: MutableState<String>,
    selectedRegister: MutableState<RegisterModel?>,
    viewModel: AdminApiViewModel,
    excluirHabilitado: Boolean,
    editarHabilitado: Boolean
) {

    var showExcludeConfirmDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column (modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1F)){
                    Text(
                        text = funcionario.nome,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF196BAD)
                    )
                }
                Row () {
                    IconButton(
                        onClick = {
                            selectedRegister.value = funcionario
                            currentPage.value = "editRegister"
                        },
                        enabled = editarHabilitado,
                        modifier = Modifier
                            .size(50.dp)

                    ) {
                        if(!editarHabilitado) {
                            Image(
                                painter = painterResource(id = R.drawable.edicao_disable),
                                contentDescription = "Editar",
                            )
                        }else {
                            Image(
                                painter = painterResource(id = R.drawable.edicao_f),
                                contentDescription = "Editar"
                            )
                        }

                    }

                    IconButton(
                        onClick = {showExcludeConfirmDialog = true},
                        enabled = excluirHabilitado,
                        modifier = Modifier
                            .height(50.dp)


                    ) {
                        if(!excluirHabilitado) {
                            Image(
                                painter = painterResource(id = R.drawable.trashcan_disable),
                                contentDescription = "Excluir",
                            )
                        }else {

                            Image(
                                painter = painterResource(id = R.drawable.trashcan_f),
                                contentDescription = "Excluir",
                            )
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Row (Modifier.padding(top = 16.dp)){
                Column(Modifier.weight(0.5F)){
                    Text( stringResource(R.string.label_position),
                        fontWeight = FontWeight.Bold,
                        color = Blue
                    )

                    Text(text = funcionario.cargo,
                        fontWeight = FontWeight.W300,
                        color = Blue,
                        modifier = Modifier.width(150.dp))

                    Spacer(modifier = Modifier.height(4.dp))



                }

                Column(Modifier.weight(0.6F)) {
                    Text(stringResource(R.string.label_email),
                    fontWeight = FontWeight.Bold,
                    color = Blue
                )

                    Text(text = funcionario.login,
                        fontWeight = FontWeight.W300,
                        color = Blue)}
            }

        }
    }

    if(showExcludeConfirmDialog){
        ExcludeConfirmationDialog(
            onDismiss = { showExcludeConfirmDialog = false },
            onConfirm = {
                viewModel.deletarFuncionario(funcionario.id, onBack = {
                    showExcludeConfirmDialog = false
                })
            }
        )
    }

}