package dev.soul.stadium_detail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.boldFont
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun AboutSection(
    modifier: Modifier = Modifier,
    about: String
) {

    var expanded by remember { mutableStateOf(false) }
    var textOverflows by remember { mutableStateOf(false) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TextView(text = "О стадион", fontFamily = boldFont(), fontSize = FontSize.EXTRA_MEDIUM)

        TextView(
            text = about,
            maxLines = if (expanded) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis,
            lineHeight = FontSize.REGULAR,
            textColor = CustomThemeManager.colors.textColor.copy(0.7f),
            modifier = Modifier
                .animateContentSize()
                .then(Modifier)
                .clickable { expanded = !expanded },
            onTextLayout = { textLayoutResult ->
                if (!expanded) {
                    textOverflows = textLayoutResult.lineCount > 4
                }
            }
        )

        if (textOverflows) {
            TextView(
                text = if (expanded) "Скрыть" else "Читать больше",
                textColor = CustomThemeManager.colors.mainColor,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}