package dev.soul.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    borderColor: Color = CustomThemeManager.colors.mainColor,
    backgroundColor: Color = CustomThemeManager.colors.screenBackground,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .background(backgroundColor, RoundedCornerShape(cornerRadius))
            .border(
                width = 1.dp,
                color = if (isFocused) borderColor else borderColor.copy(alpha = 0.0f),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .fillMaxWidth(),
        textStyle = LocalTextStyle.current.copy(
            color = CustomThemeManager.colors.textColor
        ),
        cursorBrush = SolidColor(borderColor),
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                TextView(
                    text = label, textColor = CustomThemeManager.colors.textColor
                )
            }
            innerTextField()
        })
}
