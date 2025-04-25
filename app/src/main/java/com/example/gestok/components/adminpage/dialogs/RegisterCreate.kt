package com.example.gestok.components.adminpage.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gestok.components.adminpage.RegisterData
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White

@Composable
fun RegisterCreate(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var editedNome by remember { mutableStateOf("") }
    var editedCargo by remember { mutableStateOf("") }
    var editedEmail by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                // -- HEADER -------------------------
                item {
                    Row(
                        Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Cadastrar Colaborador", fontWeight = W600, color = Blue, fontSize = 20.sp
                        )
                        Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = White)
                        }
                    }
                }

                //--- NOME COLABORADOR -------------------------------------------
                item {
                    Column(Modifier.fillMaxWidth()) {
                        Text("Nome", Modifier.padding(start = 20.dp), fontWeight = W600, color = Blue)
                        TextField(
                            value = editedNome,
                            onValueChange = { editedNome = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray,
                                unfocusedContainerColor = LightGray,
                                focusedTextColor = Black,
                                unfocusedTextColor = Black,
                                focusedIndicatorColor = LightGray,
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier.padding(start = 20.dp, bottom = 15.dp).clip(shape = RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                }

                //--- EMAIL COLABORADOR -------------------------------------------
                item {
                    Column(Modifier.fillMaxWidth()) {
                        Text("Email", Modifier.padding(start = 20.dp), fontWeight = W600, color = Blue)
                        TextField(
                            value = editedEmail,
                            onValueChange = { editedEmail = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray,
                                unfocusedContainerColor = LightGray,
                                focusedTextColor = Black,
                                unfocusedTextColor = Black,
                                focusedIndicatorColor = LightGray,
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier.padding(start = 20.dp, bottom = 15.dp).clip(shape = RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                }

                //--- CARGO COLABORADOR -------------------------------------------
                item {
                    Column(Modifier.fillMaxWidth()) {
                        Text("Cargo", Modifier.padding(start = 20.dp), fontWeight = W600, color = Blue)
                        TextField(
                            value = editedCargo,
                            onValueChange = { editedCargo = it },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = LightGray,
                                unfocusedContainerColor = LightGray,
                                focusedTextColor = Black,
                                unfocusedTextColor = Black,
                                focusedIndicatorColor = LightGray,
                                unfocusedIndicatorColor = LightGray
                            ),
                            modifier = Modifier.padding(start = 20.dp, bottom = 15.dp).clip(shape = RoundedCornerShape(20)),
                            singleLine = true
                        )
                    }
                }

                //--- BOTÃO CONFIRMAR -------------------------------------------
                item {
                    Row(
                        Modifier.fillMaxWidth().padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Blue)) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null, tint = White)
                            Text("Cancelar", color = White)
                        }
                        Button(
                            onClick = { onConfirm(editedNome, editedCargo, editedEmail); onDismiss() },
                            colors = ButtonDefaults.buttonColors(LightBlue)
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = White)
                            Text("Concluir", color = White)
                        }
                    }
                }
            }
        }
    }
}