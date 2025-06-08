package com.example.gestok.presentation.component.screens.internalScreens.order

import com.example.gestok.presentation.component.SkeletonLoader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.R
import com.example.gestok.presentation.component.orderpage.OrderCard
import com.example.gestok.domain.model.order.OrderModel
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import com.example.gestok.presentation.viewmodel.order.OrderApiViewModel


@Composable
fun OrderContent(
    modifier: Modifier = Modifier,
    viewModel: OrderApiViewModel,
    currentPage: MutableState<String>,
    selectedOrder: MutableState<OrderModel?>
) {

    val erroPedidos = viewModel.pedidosErro
    val pedidos = viewModel.pedidos
    val carregando = viewModel.carregouPedidos

    LaunchedEffect(Unit) {
        viewModel.getPedidos()
    }

    LazyColumn(
        modifier = modifier
            .background(LightGray)

    ) {

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(R.string.title_orders),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black
                    )
                    Button(
                        onClick = { currentPage.value = "createOrder" },
                        enabled =  viewModel.sessaoUsuario.cargo == "ADMIN",
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text(stringResource(R.string.order_register_text), color = White)
                    }
                }

                if (erroPedidos != null) {
                    Text(
                        erroPedidos,
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
                            SkeletonLoader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(235.dp),
                            )
                        }
                    }
                }

                pedidos.isEmpty() -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 250.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.no_orders_msg),
                            fontSize = 16.sp,
                            color = Black
                        )
                    }
                }

                else -> {
                    val statusOrder = listOf("Pendente", "Em Produção", "Concluído", "Cancelado")

                    pedidos
                        .sortedBy { statusOrder.indexOf(it.status) }
                        .forEach { pedido ->
                            OrderCard(
                                pedido = pedido,
                                currentPage = currentPage,
                                selectedOrder = selectedOrder,
                                viewModel = viewModel,
                                editarHabilitado = viewModel.sessaoUsuario.cargo in listOf("ADMIN", "SUPERVISOR")
                            )
                        }
                }
            }
        }

    }
}