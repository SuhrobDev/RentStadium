package dev.soul.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun AuthHeader(modifier: Modifier = Modifier, text: String, subText: String) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextView(
            text = text,
            textColor = CustomThemeManager.colors.textColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = FontSize.REGULAR
        )
        TextView(
            text = subText,
            textColor = CustomThemeManager.colors.textColor,
            fontWeight = FontWeight.Normal,
            fontSize = FontSize.REGULAR
        )
    }
}

