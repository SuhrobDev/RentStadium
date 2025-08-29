package dev.soul.shared.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Advanced shimmer effects with different animation styles
 */

// Pulse shimmer effect
fun Modifier.pulseShimmer(
    isLoading: Boolean = true,
    color: Color = Color.LightGray.copy(alpha = 0.6f),
    shape: Shape = RoundedCornerShape(4.dp)
): Modifier = composed {
    if (!isLoading) return@composed this

    val transition = rememberInfiniteTransition(label = "pulse")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    background(
        color = color.copy(alpha = alpha.value),
        shape = shape
    )
}

// Wave shimmer effect
fun Modifier.waveShimmer(
    isLoading: Boolean = true,
    colors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    ),
    shape: Shape = RoundedCornerShape(4.dp)
): Modifier = composed {
    if (!isLoading) return@composed this

    val transition = rememberInfiniteTransition(label = "wave")
    val translateAnimation = transition.animateFloat(
        initialValue = -400f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_translate"
    )

    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(translateAnimation.value - 200f, 0f),
            end = Offset(translateAnimation.value + 200f, 0f)
        ),
        shape = shape
    )
}

// Skeleton shimmer with multiple highlights
fun Modifier.skeletonShimmer(
    isLoading: Boolean = true,
    baseColor: Color = Color.LightGray.copy(alpha = 0.3f),
    highlightColor: Color = Color.White.copy(alpha = 0.7f),
    shape: Shape = RoundedCornerShape(4.dp)
): Modifier = composed {
    if (!isLoading) return@composed this

    val transition = rememberInfiniteTransition(label = "skeleton")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "skeleton_translate"
    )

    val colors = listOf(
        baseColor,
        baseColor,
        highlightColor,
        baseColor,
        baseColor
    )

    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(translateAnimation.value - 300f, translateAnimation.value - 300f),
            end = Offset(translateAnimation.value, translateAnimation.value)
        ),
        shape = shape
    )
}