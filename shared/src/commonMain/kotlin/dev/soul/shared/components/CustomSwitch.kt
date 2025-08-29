package dev.soul.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    trackWidth: Dp,
    trackHeight: Dp
) {
    val thumbSize = trackHeight - 1.dp  // Padding for thumb to fit inside the track
    val switchPadding = 4.dp

    Box(
        modifier = modifier
            .width(trackWidth)
            .height(trackHeight)
            .clip(RoundedCornerShape(50))
            .background(
                if (checked) CustomThemeManager.colors.mainColor else CustomThemeManager.colors.lightGray
            )
            .clickable {
                onCheckedChange(!checked)
            }, contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 1.dp)
                .size(thumbSize)
                .clip(CircleShape)
                .background(Color.White)
                .shadow(8.dp, CircleShape)
                .padding(switchPadding)
        )
    }
}

//@Preview
@Composable
private fun PreviewMySwitch() {
    CustomSwitch(
        checked = false, onCheckedChange = {
        }, trackWidth = 45.dp, trackHeight = 20.dp
    )
}