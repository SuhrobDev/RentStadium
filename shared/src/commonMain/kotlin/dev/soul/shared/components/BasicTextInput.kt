package dev.soul.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun BasicTextInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    maxLines: Int = 1,
    isComment: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .fillMaxWidth()
            .background(
                CustomThemeManager.colors.lightGray,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (isFocused) CustomThemeManager.colors.mainColor else CustomThemeManager.colors.mainColor.copy(
                    alpha = 0.0f
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        value = value,
        onValueChange = { new ->
            onValueChange(new)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        cursorBrush = SolidColor(Color.Transparent),
        textStyle = TextStyle(color = Color.Transparent, fontSize = 18.sp),
        decorationBox = { inner ->
            val display = value.ifEmpty {
                hint
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = if (isComment) Alignment.Top else Alignment.CenterVertically
            ) {
                TextView(
                    text = display,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textColor = if (value.isEmpty())
                        CustomThemeManager.colors.hintColor
                    else
                        CustomThemeManager.colors.textColor
                )
            }
            inner()
        },
        maxLines = maxLines
    )
}