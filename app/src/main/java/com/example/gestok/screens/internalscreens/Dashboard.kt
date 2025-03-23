package com.example.gestok.screens.internalscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestok.components.LineChartScreen
import com.example.gestok.components.PieChartScreen
import com.example.gestok.ui.theme.Black
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.White

@Composable
fun Dashboard() {

    var kpiMediaAvaliacao by remember { mutableDoubleStateOf(8.0) }
    var kpiPedidosAbertos by remember { mutableIntStateOf(2) }
    var kpiValorMedioPedidos by remember { mutableDoubleStateOf(50.0) }

    var kpiFaturamentoMesAtual by remember { mutableDoubleStateOf(300.0) }
    var kpiFaturamentoMesAnterior by remember { mutableDoubleStateOf(250.0) }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,) {

        Text("Gráficos e Visualizações",
            color = Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 30.dp, top = 28.dp))

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier
            .width(350.dp)
            .height(520.dp)
        ) {

           item {

               Column(
                   modifier = Modifier
                       .fillMaxSize(),
               ) {

                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(115.dp)
                   ){

                       Box(
                           modifier = Modifier
                               .shadow(6.dp, RoundedCornerShape(8.dp))
                               .background(White)
                               .fillMaxHeight()
                               .weight(0.6f),
                       ) {
                           Column(
                               modifier = Modifier
                                   .padding(6.dp)
                                   .fillMaxHeight(),
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.SpaceBetween
                           ) {
                               Text(
                                   "Pedido em aberto para os próximos 7 dias",
                                   fontSize = 12.sp,
                                   fontWeight = FontWeight.Bold,
                                   color = Black
                               )

                                   Text(
                                       text = kpiPedidosAbertos.toString(),
                                       fontSize = 20.sp,
                                       fontWeight = FontWeight.Bold,
                                       color = LightBlue,
                                       modifier = Modifier

                                   )
                           }
                       }

                       Spacer(modifier = Modifier.width(8.dp))

                       Box(
                           modifier = Modifier
                               .shadow(6.dp, RoundedCornerShape(8.dp))
                               .background(LightBlue)
                               .fillMaxHeight()
                               .weight(0.4f)
                       ) {
                           Column(
                               modifier = Modifier
                                   .padding(6.dp)
                                   .fillMaxHeight(),
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.SpaceBetween
                           ) {
                               Text(
                                   "Média de Avaliação",
                                   fontSize = 12.sp,
                                   fontWeight = FontWeight.Bold,
                                   color = White
                               )

                               Row(
                                   verticalAlignment = Alignment.CenterVertically,
                               ) {
                                   Icon(
                                       imageVector = Icons.Default.Star,
                                       contentDescription = "Estrela de avaliação",
                                       tint = Color.White,
                                       modifier = Modifier
                                           .size(30.dp)
                                           .clip(RoundedCornerShape(6.dp))
                                   )

                                   Text(
                                       text = kpiMediaAvaliacao.toString(),
                                       fontSize = 20.sp,
                                       fontWeight = FontWeight.Bold,
                                       color = White
                                   )
                               }
                           }
                       }

                   }

                   Spacer(modifier = Modifier.height(15.dp))

                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(115.dp)
                   ) {

                       Box(
                           modifier = Modifier
                               .shadow(6.dp, RoundedCornerShape(8.dp))
                               .background(White)
                               .fillMaxHeight()
                               .weight(0.4f)
                       ) {
                           Column(
                               modifier = Modifier
                                   .padding(6.dp)
                                   .fillMaxHeight(),
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.SpaceBetween
                           ) {
                               Text(
                                   "Valor médio dos pedidos (R$)",
                                   fontSize = 12.sp,
                                   fontWeight = FontWeight.Bold,
                                   color = Black
                               )


                               Text(
                                   text = kpiValorMedioPedidos.toString(),
                                   fontSize = 20.sp,
                                   fontWeight = FontWeight.Bold,
                                   color = LightBlue

                               )
                           }
                       }

                       Spacer(modifier = Modifier.width(8.dp))

                       Box(
                           modifier = Modifier
                               .shadow(6.dp, RoundedCornerShape(8.dp))
                               .background(White)
                               .fillMaxHeight()
                               .weight(0.6f)
                       ) {
                           Column(
                               modifier = Modifier
                                   .padding(6.dp)
                                   .fillMaxHeight(),
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.SpaceBetween
                           ) {
                               Text(
                                   "Faturamento do mês atual (R$)",
                                   fontSize = 12.sp,
                                   fontWeight = FontWeight.Bold,
                                   color = Black
                               )


                               Text(
                                   text = "R$ $kpiFaturamentoMesAtual",
                                   fontSize = 20.sp,
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

                   Spacer(modifier = Modifier.height(15.dp))

                   PieChartScreen(
                       title = "Quantidade de Pedidos por Status",
                       data = listOf(30f, 25f, 20f, 15f),
                   )

                   Spacer(modifier = Modifier.height(15.dp))

                   LineChartScreen(
                       title = "Valor Arrecadado por Mês (R$)",
                       data = listOf(20f, 250f, 300f),
                       xLabels = listOf("Jan 2025", "Fev 2025", "Mar 2025")
                   )

                   Spacer(modifier = Modifier.height(15.dp))

                   LineChartScreen(
                       title = "Quantidade de pedidos por mês",
                       data = listOf(5, 25, 50),
                       xLabels = listOf("Jan 2025", "Fev 2025", "Mar 2025")
                   )

               }

           }

        }

    }

}