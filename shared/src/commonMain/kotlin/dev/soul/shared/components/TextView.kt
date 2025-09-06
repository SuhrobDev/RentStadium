package dev.soul.shared.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import dev.soul.shared.FontSize
import dev.soul.shared.regularFont
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun TextView(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = CustomThemeManager.colors.textColor,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = regularFont(),
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    textDecoration: TextDecoration = TextDecoration.None,
    fontSize: TextUnit = FontSize.SMALL,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
//    style: TextStyle = TextStyle(
//        platformStyle = PlatformTextStyle(
//            includeFontPadding = false,
//        ),
//    ),
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        text = text,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = textColor,
        textAlign = textAlign,
        modifier = modifier,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        lineHeight = lineHeight,
//        style = style,
        textDecoration = textDecoration
    )
}