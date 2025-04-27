package com.example.gestok.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White

@Composable
fun CancelConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    externalOnDismiss: () -> Unit
){

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        Modifier.fillMaxWidth().padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Cancelar", fontWeight = W600, color = Blue, fontSize = 25.sp)
                    }

                    Row(   Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center){
                        Text("Você tem certeza que deseja cancelar a operação sem salvar?", textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        Modifier.fillMaxWidth().padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ){
                        Button(
                            onClick = {onDismiss()},
                            colors = ButtonDefaults.buttonColors(LightBlue)
                        ) {

                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = White,

                                )
                            Text("Não")
                        }
                        Button(
                            onClick = {onConfirm()
                                        externalOnDismiss()
                                      },
                            colors = ButtonDefaults.buttonColors(Blue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = White,

                                )
                            Text("Sim")
                        }
                    }
                }


            }
        }

    }


}