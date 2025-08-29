package dev.soul.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.soul.shared.FontSize
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun NameView(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            onValueChange(newValue.text.trim())
        },
        textStyle = TextStyle(
            color = CustomThemeManager.colors.textColor,
            fontSize = FontSize.REGULAR,
            fontWeight = FontWeight.Medium
        ),
        singleLine = true,
        cursorBrush = SolidColor(CustomThemeManager.colors.textColor),
        modifier = modifier
            .fillMaxWidth()
            .background(
                CustomThemeManager.colors.lightGray, shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (isFocused) CustomThemeManager.colors.mainColor else CustomThemeManager.colors.mainColor.copy(
                    alpha = 0.0f
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        enabled = enabled,
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (textFieldValue.text.isEmpty()) {
                        TextView(
                            text = "Имя Фамилия",
                            textColor = CustomThemeManager.colors.hintColor,
                            fontSize = 18.sp
                        )
                    }
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(8.dp))
            }
        })
}

//@Preview
@Composable
private fun PreviewNameView() {
    NameView(value = "", onValueChange = {})
}