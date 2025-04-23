package com.example.gestok.screens.internalScreens.dashboard

import android.util.Log
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
import com.example.gestok.screens.login.LoggedInUser
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGray
import com.example.gestok.ui.theme.White
import com.example.gestok.viewModel.dashboard.DashboardApiViewModel
import org.koin.compose.koinInject

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

    val erroDashboard = viewModel.dashboardErro

    val x = koinInject<LoggedInUser>()

    Log.d("vhtest", "USUARIO ID EMPRESA:" + x)


    LaunchedEffect(Unit) {
        viewModel.buscarTodos()
    }

    LaunchedEffect(viewModel.carregouPedidos) {
        if (viewModel.carregouPedidos) {

            kpiPedidosAbertos =  viewModel.buscarPedidosProximos7Dias().size
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
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

                if(erroDashboard != null) {
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
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = kpiPedidosAbertos.toString(),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LightBlue,
                                    modifier = Modifier

                                )
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
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Estrela de avaliação",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                )

                                Text(
                                    text = kpiMediaAvaliacao.toString(),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = White
                                )
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
                            Text(
                                "Valor médio dos pedidos (R$)",

                                fontWeight = FontWeight.Bold,
                                color = Black
                            )


                            Text(
                                text = kpiValorMedioPedidos.toString(),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = LightBlue

                            )
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
                            Text(
                                "Faturamento do mês atual (R$)",

                                fontWeight = FontWeight.Bold,
                                color = Black
                            )


                            Text(
                                text = "R$ $kpiFaturamentoMesAtual",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = LightBlue

                            )

                            Text(
                                text = "Ultimo mês: R$$kpiFaturamentoMesAnterior",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black

                            )
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
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                    ) {

                        PieChartScreen(
                            title = "Quantidade de Pedidos por Status",
                            data = listOf(30f, 25f, 20f, 15f),
                        )
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
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                    ) {
                        LineChartScreen(
                            title = "Valor Arrecadado por Mês (R$)",
                            data = listOf(20f, 250f, 300f),
                            xLabels = listOf("Jan 2025", "Fev 2025", "Mar 2025")
                        )
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
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                    ) {
                        LineChartScreen(
                            title = "Quantidade de pedidos por mês",
                            data = listOf(5, 25, 50),
                            xLabels = listOf("Jan 2025", "Fev 2025", "Mar 2025")
                        )
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }


    }
}





