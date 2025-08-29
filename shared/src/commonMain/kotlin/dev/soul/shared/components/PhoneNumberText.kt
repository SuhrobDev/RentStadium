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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.soul.shared.FontSize
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun PhoneNumberTextField(
    phone: String, enabled: Boolean, onPhoneChange: (String) -> Unit
) {
    val maxLength = 12
    var firstInitial by remember { mutableStateOf(true) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(phone.removePrefix("+998 "))) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(firstInitial) {

        if (firstInitial) {
            textFieldValue.copy(text = phone)
            firstInitial = false
        }

//        Log.e("cxzpinqw", "PhoneNumberTextField: $phone")
//        Log.e("cxzpinqw", "PhoneNumberTextField: ${textFieldValue.text}")

    }

    BasicTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val cleaned = newValue.text.filter { it.isDigit() }
            if (cleaned.length <= maxLength) {
                val formattedNumber = formatUzbekPhoneNumber(
                    cleaned
                )
                val finalText = formattedNumber

                textFieldValue = newValue.copy(
                    text = formattedNumber, selection = TextRange(formattedNumber.length)
                )

                onPhoneChange.invoke(finalText)
            }
        },
        textStyle = TextStyle(
            color = CustomThemeManager.colors.textColor, fontSize = FontSize.REGULAR,
            fontWeight = FontWeight.Medium
        ),
        singleLine = true,
        cursorBrush = SolidColor(CustomThemeManager.colors.textColor),
        modifier = Modifier
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
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
        enabled = enabled,
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isFocused) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(Modifier.weight(1f)) {
                        if (textFieldValue.text.isEmpty()) {
                            TextView(
                                text = "12 345 67 89",
                                textColor = CustomThemeManager.colors.hintColor,
                                fontSize = 18.sp
                            )
                        }
                        innerTextField()
                    }
                } else {
                    if (phone.isEmpty()) {
                        TextView(
                            text = "12 345 67 89",
                            textColor = CustomThemeManager.colors.hintColor,
                            fontSize = 18.sp
                        )
                    } else {
                        TextView(
                            text = "${phone.removePrefix("+998 ")}",
                            textColor = CustomThemeManager.colors.textColor,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        })
}

private fun formatUzbekPhoneNumber(input: String): String {
    val digits = input.filter { it.isDigit() }
    val formatted = StringBuilder()

    if (digits.isNotEmpty()) formatted.append(digits.take(2))
    if (digits.length > 2) formatted.append(" ").append(digits.drop(2).take(3))
    if (digits.length > 5) formatted.append(" ").append(digits.drop(5).take(2))
    if (digits.length > 7) formatted.append(" ").append(digits.drop(7).take(2))

    return formatted.toString()
}

//@Preview(showBackground = true)
@Composable
private fun PhoneNumberInputFieldPreview() {
    PhoneNumberTextField(phone = "+998 90 123 45 67", onPhoneChange = {}, enabled = true)
}
