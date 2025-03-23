package com.example.gestok.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData

@Composable
fun PieChartScreen() {
    var selectedSlice by remember { mutableStateOf<Pair<String, Int>?>(null) }

    val slices = listOf(
        PieChartData.Slice("Pendente", 30f, Color(0xFF196BAD)),
        PieChartData.Slice("Em Produção", 25f, Color(0xFF4CAF50)),
        PieChartData.Slice("Concluído", 20f, Color(0xFFFFD700)),
        PieChartData.Slice("Cancelado", 15f, Color(0xFFEF5350))
    )

    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        backgroundColor = Color.Transparent,
        showSliceLabels = false
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Quantidade de pedidos por status",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            slices.forEach { slice ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(slice.color, shape = RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = slice.label, fontSize = 10.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        PieChart(
            modifier = Modifier
                .size(380.dp)
                .align(Alignment.CenterHorizontally),
            pieChartData,
            pieChartConfig,
            onSliceClick = { slice ->
                selectedSlice = slice.label to slice.value.toInt()
            }
        )

        selectedSlice?.let { (label, value) ->
            val color = slices.find { it.label == label }?.color ?: Color.Black
            Text(
                text = "$label: $value",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

