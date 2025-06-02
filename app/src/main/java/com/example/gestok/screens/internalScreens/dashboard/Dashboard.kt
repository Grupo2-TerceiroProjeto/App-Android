package com.example.gestok.screens.internalScreens.dashboard

import SkeletonLoader
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.LineChartScreen
import com.example.gestok.components.PieChartScreen
import com.example.gestok.screens.internalScreens.dashboard.data.OrderStatus
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.dashboard.DashboardApiViewModel
import java.util.Locale;

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    viewModel: DashboardApiViewModel
) {

    var kpiMediaAvaliacao by remember { mutableDoubleStateOf(0.0) }
    var kpiPedidosAbertos by remember { mutableIntStateOf(0) }
    var kpiValorMedioPedidos by remember { mutableDoubleStateOf(0.0) }

    var kpiFaturamentoMesAtual by remember { mutableDoubleStateOf(0.0) }
    var kpiFaturamentoMesAnterior by remember { mutableDoubleStateOf(0.0) }

    var pedidosPorStatus by remember {
        mutableStateOf(OrderStatus(0f, 0f, 0f, 0f))
    }

    val (faturamentos, meses) = viewModel.getFaturamentoUltimos6Meses()
    val (quantidadesPedidos, mesesPedidos) = viewModel.getPedidosPorMes()

    val erroDashboard = viewModel.dashboardErro

    LaunchedEffect(Unit) {
        viewModel.getPedidos()
    }

    LaunchedEffect(viewModel.carregouPedidos) {
        if (viewModel.carregouPedidos) {

            kpiPedidosAbertos = viewModel.getBuscarPedidosProximos7Dias().size
            kpiValorMedioPedidos = viewModel.getValorMedioPedidos()
            kpiFaturamentoMesAtual = viewModel.getFaturamentoMesAtual()
            kpiFaturamentoMesAnterior = viewModel.getFaturamentoMesAnterior()
            pedidosPorStatus = viewModel.getPedidosPorCategoria()

            viewModel.getMediaAvaliacao()

        }
    }

    kpiMediaAvaliacao = viewModel.mediaAvaliacao

    LazyColumn(
        modifier = modifier
            .background(LightGray)

    ) {

        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 30.dp)
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Dashboard",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = Black,
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                    )
                }

                if (erroDashboard != null) {
                    Text(
                        erroDashboard ?: "",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Red,
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                    )
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    //-- 1 CARD ---------------------------------------------
                    Card(
                        modifier = Modifier
                            .weight(0.5F)
                            .height(160.dp)
                            .padding(end = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    )
                    {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Row {
                                Text(
                                    "Pedido em aberto para os próximos 7 dias",
                                    fontWeight = FontWeight.Bold,
                                    color = Black
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (!viewModel.carregouPedidos) {
                                    SkeletonLoader(
                                        modifier = Modifier
                                    )
                                } else {
                                    Text(
                                        text = kpiPedidosAbertos.toString(),
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LightBlue
                                    )
                                }

                            }
                        }

                    }

                    //-- 2 CARD ---------------------------------------------

                    Card(
                        modifier = Modifier
                            .weight(0.5F)
                            .height(160.dp)
                            .padding(start = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Blue
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Média de Avaliação",
                                fontWeight = FontWeight.Bold,
                                color = White
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                if (!viewModel.carregouPedidos) {
                                    SkeletonLoader(
                                        modifier = Modifier
                                    )
                                } else {

                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Estrela de avaliação",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(35.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                    )

                                    Text(
                                        text = String.format(Locale.US, "%.1f", kpiMediaAvaliacao),
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = White
                                    )

                                }

                            }
                        }
                    }

                }



                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    //-- 3 CARD ---------------------------------------------
                    Card(
                        modifier = Modifier
                            .weight(0.5F)
                            .height(160.dp)
                            .padding(end = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Row {

                                Text(
                                    "Valor médio dos pedidos (R$)",

                                    fontWeight = FontWeight.Bold,
                                    color = Black
                                )

                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                if(!viewModel.carregouPedidos) {
                                    SkeletonLoader(
                                        modifier = Modifier
                                    )
                                } else {


                                    Text(
                                        text = kpiValorMedioPedidos.toString(),
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LightBlue

                                    )

                                }

                            }
                        }
                    }


                    //-- 4 CARD ---------------------------------------------
                    Card(
                        modifier = Modifier
                            .weight(0.5F)
                            .height(160.dp)
                            .padding(start = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Row {

                                Text(
                                    "Faturamento do mês atual (R$)",

                                    fontWeight = FontWeight.Bold,
                                    color = Black
                                )
                            }

                            Column (
                               verticalArrangement = Arrangement.Center,
                               horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                if(!viewModel.carregouPedidos) {
                                    SkeletonLoader(
                                        modifier = Modifier
                                    )
                                } else {

                                    Text(
                                        text = "R$ $kpiFaturamentoMesAtual",
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LightBlue

                                    )

                                    Text(
                                        text = "Ultimo mês: R$$kpiFaturamentoMesAnterior",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Black

                                    )
                                }

                            }
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }

        //---- PIE CHART ------------------------------------

        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Quantidade de Pedidos por Status",
                            color = Black,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (!viewModel.carregouPedidos) {

                            SkeletonLoader(modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp))
                        } else {


                            PieChartScreen(
                                data = listOf(
                                    pedidosPorStatus.pendente,
                                    pedidosPorStatus.emProducao,
                                    pedidosPorStatus.concluido,
                                    pedidosPorStatus.cancelado
                                )
                            )
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            ) {
                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Valor faturado por Mês (R$)",
                            color = Black,
                            fontWeight = FontWeight.Bold
                        )


                        if (!viewModel.carregouPedidos) {

                            SkeletonLoader(modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp))
                        } else {

                            LineChartScreen(
                                data = faturamentos,
                                xLabels = meses
                            )

                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Text(
                            "Quantidade de pedidos por mês",
                            color = Black,
                            fontWeight = FontWeight.Bold
                        )


                        if (!viewModel.carregouPedidos) {

                            SkeletonLoader(modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp))
                        } else {

                            LineChartScreen(
                                data = quantidadesPedidos,
                                xLabels = mesesPedidos
                            )

                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }


    }
}





