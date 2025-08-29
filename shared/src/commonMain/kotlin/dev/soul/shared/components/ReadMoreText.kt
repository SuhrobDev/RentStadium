package dev.soul.shared.components

import androidx.compose.foundation.clickable
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
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun ReadMoreText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 4
) {
    var isExpanded by remember { mutableStateOf(false) }
    var canExpand by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        TextView(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            onTextLayout = {
                if (!isExpanded) {
                    canExpand = it.lineCount > minimizedMaxLines
                }
            },
            overflow = TextOverflow.Ellipsis,
        )

        if (canExpand) {
            Spacer(modifier = Modifier.height(4.dp))
            TextView(
                text = if (isExpanded) "Read less" else "Read more",
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded },
                textColor = CustomThemeManager.colors.mainColor
            )
        }
    }
}