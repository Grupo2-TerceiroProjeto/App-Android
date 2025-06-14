package com.example.gestok.presentation.component

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
import com.example.gestok.ui.theme.Blue
import com.example.gestok.ui.theme.LightBlue
import com.example.gestok.ui.theme.LightGrayPieChart
import com.example.gestok.ui.theme.MediumGray
import com.example.gestok.ui.theme.White

@Composable
fun PieChartScreen(
    data: List<Float>
) {
    var selectedSlice by remember { mutableStateOf<Pair<String, Int>?>(null) }

    val slices = listOf(
        PieChartData.Slice("Pendente", data.getOrElse(0) { 0f }, LightBlue),
        PieChartData.Slice("Em Produção", data.getOrElse(1) { 0f }, Blue),
        PieChartData.Slice("Concluído", data.getOrElse(2) { 0f }, LightGrayPieChart),
        PieChartData.Slice("Cancelado", data.getOrElse(3) { 0f }, MediumGray)
    )

    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Donut
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        backgroundColor = White,
        showSliceLabels = false
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(White)
    ) {

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

        selectedSlice?.let { (label, value) ->
            val color = slices.find { it.label == label }?.color ?: Color.Black
            Text(
                text = "$label: $value",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        PieChart(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                ,
            pieChartData,
            pieChartConfig,
            onSliceClick = { slice ->
                selectedSlice = slice.label to slice.value.toInt()
            }
        )
    }
}

