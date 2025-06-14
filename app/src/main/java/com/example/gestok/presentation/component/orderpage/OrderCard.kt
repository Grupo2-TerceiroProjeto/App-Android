package com.example.gestok.presentation.component.orderpage

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.presentation.component.orderpage.popups.InfoDialog
import com.example.gestok.presentation.component.orderpage.popups.Recipe
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.domain.model.product.IngredientsFormat
import com.example.gestok.ui.theme.Blue
import com.example.gestok.utils.formatPhoneNumber
import com.example.gestok.presentation.viewmodel.order.OrderApiViewModel

@Composable
fun OrderCard(
    pedido: OrderModel,
    currentPage: MutableState<String>,
    selectedOrder: MutableState<OrderModel?>,
    viewModel: OrderApiViewModel,
    editarHabilitado: Boolean
) {

    val enabled = pedido.status == "Cancelado" || pedido.status == "Concluído"

    var showInfoDialog by remember { mutableStateOf(false) }

    var showRecipeDialog by remember { mutableStateOf(false) }

    var receita by remember { mutableStateOf<List<IngredientsFormat>>(emptyList()) }
    var loadingReceita by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, //COR DO CARD
        ),


        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.label_order_applicant), fontWeight = FontWeight.Bold, color = Blue)
                    Text(pedido.nomeSolicitante, fontWeight = FontWeight.W300, color = Blue)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.label_order_contact), fontWeight = FontWeight.Bold, color = Blue)
                    Text(
                        formatPhoneNumber(pedido.telefone),
                        fontWeight = FontWeight.W300,
                        color = Blue
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.label_order_status), fontWeight = FontWeight.Bold, color = Blue)
                    Text(pedido.status, fontWeight = FontWeight.W300, color = Blue)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.label_order_delivery_date), fontWeight = FontWeight.Bold, color = Blue)
                    Text(pedido.dataEntrega ?: "", fontWeight = FontWeight.W300, color = Blue)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Column(Modifier.padding(top = 16.dp)) {
                Row{
                    Column {
                        pedido.produtos
                            .filter { it.quantidade > 0 }
                            .forEach { item ->
                                Text(
                                    text = "• ${item.nome} ${item.quantidade}un",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500,
                                    color = Blue
                                )
                            }
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.Bottom),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row() {
                            if (!enabled) {

                                IconButton(
                                    onClick = {
                                        selectedOrder.value = pedido
                                        currentPage.value = "editOrder"
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
                                            contentDescription = "Editar",
                                        )
                                    }

                                }
                            }

                            if (enabled) {
                                IconButton(
                                    onClick = { showInfoDialog = true },
                                    modifier = Modifier
                                        .size(50.dp)
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.info),
                                        contentDescription = "Informação do pedido"
                                    )
                                }
                            }

                            IconButton(
                                onClick = {
                                    loadingReceita = true
                                    receita = emptyList()

                                    viewModel.getReceita(pedido) { ingredientes ->
                                        receita = ingredientes
                                        loadingReceita = false
                                        showRecipeDialog = true
                                    }
                                },
                                modifier = Modifier.height(50.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.menu_f),
                                    contentDescription = "Receita",
                                )
                            }
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

        }

    }

    if (showInfoDialog) {
        InfoDialog(
            message = stringResource(R.string.order_info_msg, pedido.status),
            onDismiss = { showInfoDialog = false }
        )
    }

    if (showRecipeDialog) {
        Recipe(
            ingredientes = receita,
            onDismiss = { showRecipeDialog = false }
        )
    }

}




