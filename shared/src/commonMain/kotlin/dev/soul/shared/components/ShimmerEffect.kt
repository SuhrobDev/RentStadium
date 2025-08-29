package dev.soul.shared.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Shimmer effect modifier that can be applied to any composable
 *
 * @param isLoading Whether to show the shimmer effect
 * @param colors List of colors for the shimmer gradient
 * @param animationSpec Animation specification for the shimmer
 * @param shape Shape of the shimmer overlay
 */
fun Modifier.shimmerEffect(
    isLoading: Boolean = true,
    colors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    ),
    animationSpec: AnimationSpec<Float> = infiniteRepeatable(
        animation = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        repeatMode = RepeatMode.Restart
    ),
    shape: Shape = RoundedCornerShape(16)
): Modifier = composed {
    if (!isLoading) return@composed this

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = animationSpec as InfiniteRepeatableSpec<Float>,
        label = "shimmer_translate"
    )

    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        ),
        shape = shape
    )
}

/**
 * Pre-built shimmer components for common use cases
 */
object ShimmerComponents {

    @Composable
    fun ShimmerBox(
        modifier: Modifier = Modifier,
        isLoading: Boolean = true,
        width: Dp = 100.dp,
        height: Dp = 20.dp,
        cornerRadius: Dp = 4.dp
    ) {
        Box(
            modifier = modifier
                .size(width = width, height = height)
                .clip(RoundedCornerShape(cornerRadius))
                .shimmerEffect(isLoading = isLoading)
        )
    }

    @Composable
    fun ShimmerText(
        modifier: Modifier = Modifier,
        isLoading: Boolean = true,
        lines: Int = 1,
        lineHeight: Dp = 16.dp,
        lineSpacing: Dp = 4.dp
    ) {
        Column(modifier = modifier) {
            repeat(lines) { index ->
                val width = when {
                    lines == 1 -> 200.dp
                    index == lines - 1 -> 150.dp // Last line shorter
                    else -> 250.dp
                }

                ShimmerBox(
                    width = width,
                    height = lineHeight,
                    isLoading = isLoading
                )

                if (index < lines - 1) {
                    Spacer(modifier = Modifier.height(lineSpacing))
                }
            }
        }
    }

    @Composable
    fun ShimmerCircle(
        modifier: Modifier = Modifier,
        isLoading: Boolean = true,
        size: Dp = 40.dp
    ) {
        Box(
            modifier = modifier
                .size(size)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .shimmerEffect(isLoading = isLoading)
        )
    }

    @Composable
    fun ShimmerCard(
        modifier: Modifier = Modifier,
        isLoading: Boolean = true,
        hasImage: Boolean = true,
        hasAvatar: Boolean = false
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Image placeholder
            if (hasImage) {
                ShimmerBox(
                    modifier = Modifier.fillMaxWidth(),
                    width = 300.dp,
                    height = 200.dp,
                    cornerRadius = 8.dp,
                    isLoading = isLoading
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Avatar and title row
            Row {
                if (hasAvatar) {
                    ShimmerCircle(
                        size = 40.dp,
                        isLoading = isLoading
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    // Title
                    ShimmerBox(
                        width = 180.dp,
                        height = 20.dp,
                        isLoading = isLoading
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Subtitle
                    ShimmerBox(
                        width = 120.dp,
                        height = 16.dp,
                        isLoading = isLoading
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Content lines
            ShimmerText(
                lines = 3,
                isLoading = isLoading
            )
        }
    }
}

/**
 * Customizable shimmer theme
 */
data class ShimmerTheme(
    val lightColors: List<Color> = listOf(
        Color(0xFFE0E0E0),
        Color(0xFFF5F5F5),
        Color(0xFFE0E0E0)
    ),
    val darkColors: List<Color> = listOf(
        Color(0xFF2A2A2A),
        Color(0xFF3A3A3A),
        Color(0xFF2A2A2A)
    ),
    val animationDuration: Int = 1000,
    val cornerRadius: Dp = 4.dp
)

@Composable
fun rememberShimmerTheme(
    lightColors: List<Color> = ShimmerTheme().lightColors,
    darkColors: List<Color> = ShimmerTheme().darkColors,
    animationDuration: Int = 1000,
    cornerRadius: Dp = 4.dp
): ShimmerTheme {
    val isSystemInDarkTheme = androidx.compose.foundation.isSystemInDarkTheme()

    return remember(isSystemInDarkTheme, lightColors, darkColors, animationDuration, cornerRadius) {
        ShimmerTheme(
            lightColors = lightColors,
            darkColors = darkColors,
            animationDuration = animationDuration,
            cornerRadius = cornerRadius
        )
    }
}