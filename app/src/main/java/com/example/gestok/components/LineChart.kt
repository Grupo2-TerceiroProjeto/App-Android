package com.example.gestok.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

@Composable
fun LineChartScreen(
    title: String,
    data: List<Number>,
    xLabels: List<String>
) {


    val isIntData = data.all { it is Int }

    val valuesAsFloat = data.map { it.toFloat() }

    val minY = valuesAsFloat.minOrNull() ?: 0f
    val maxY = valuesAsFloat.maxOrNull() ?: 500f

    val step = (maxY - minY) / 4
    val yLabels = List(5) { index ->

        if (isIntData) {
            (minY + step * index).toInt().toString()
        } else {
            (minY + step * index).toString()
        }
    }

    val pointsData = valuesAsFloat.mapIndexed { index, value ->
        Point(index.toFloat(), value)
    }

    val steps = yLabels.size - 1

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData { i -> xLabels.getOrElse(i) { "" } }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i -> yLabels.getOrElse(i) { "" } }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = MaterialTheme.colorScheme.outlineVariant),
        backgroundColor = MaterialTheme.colorScheme.surface
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            title,
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            lineChartData = lineChartData
        )
    }
}



