package dev.soul.shared.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.Shapes
import dev.soul.shared.Sizes
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.theme.MainGreenColor
import dev.soul.shared.theme.ScreenBackgroundColorLight

@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    height: Dp = Sizes.buttenHeight,
    cornerRadius: Dp = Shapes.defaultCorner,
    containerColor: Color = MainGreenColor,
    textSize: TextUnit = FontSize.REGULAR,
    textColor: Color = CustomThemeManager.colors.textColor,
    enabled: Boolean = true,
    isBordered: Boolean = false,
    borderColor: Color = ScreenBackgroundColorLight,
    text: String = /*stringResource(SharedRes.strings.next)*/"Next", isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = {
            if (!isLoading)
                onClick()
        },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = CustomThemeManager.colors.disabledButtonColor,
        ),
        border = if (!isBordered) null else BorderStroke(1.dp, borderColor),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (isLoading)
            CircularProgressIndicator(
                trackColor = CustomThemeManager.colors.lightGray,
                modifier = Modifier.size(28.dp),
                strokeWidth = 3.dp,
                color = Color.White,
            )
        else
            TextView(
                text = text,
                fontSize = textSize,
                textColor = if (!enabled) CustomThemeManager.colors.textColor.copy(alpha = 0.7f) else textColor,
                fontWeight = FontWeight.Medium
            )
    }
}