package com.example.gestok.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity

@Composable
fun SkeletonLoader(
    modifier: Modifier = Modifier,
    width: Dp = 80.dp,
    height: Dp = 30.dp
) {
    val shimmerAnim = remember { Animatable(0f) }

    val density = LocalDensity.current.density

    LaunchedEffect(key1 = Unit) {
        while (true) {
            shimmerAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1500, delayMillis = 500)
            )
            shimmerAnim.snapTo(0f)
        }
    }

    val widthPx = width.value * density
    val heightPx = height.value * density

    Spacer(
        modifier = modifier
            .size(width, height)
            .clip(RoundedCornerShape(4.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.LightGray.copy(alpha = 0.3f),
                        Color.LightGray.copy(alpha = 0.8f)
                    ),
                    start = Offset(-widthPx, 0f),
                    end = Offset(shimmerAnim.value * 2 * widthPx, 0f)
                )
            )
    )
}
