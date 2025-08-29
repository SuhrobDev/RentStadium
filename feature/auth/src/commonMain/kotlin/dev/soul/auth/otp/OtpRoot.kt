package dev.soul.auth.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.BaseToolbar
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.OtpInputField22
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun OtpRoot(modifier: Modifier = Modifier) {
    Content(
        focusRequesters = List(5) { FocusRequester() }
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    focusRequesters: List<FocusRequester>,
) {
    BaseBox {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {

                Column {
                    BaseToolbar(name = "", onBack = {})
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        TextView(
                            text = "Введите код из СМС",
                            fontSize = FontSize.EXTRA_MEDIUM,
                            fontWeight = FontWeight.Medium
                        )

                        TextView(
                            text = "Мы отправили его на номер *** 99",
                            textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.6f)
                        )

                        Row(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {
                            repeat(5) {
                                OtpInputField22(
                                    number = 5,
                                    focusRequester = focusRequesters[1],
                                    onFocusChanged = { isFocused ->
                                        if (isFocused) {
//                                    onAction(VerifyIntent.OnChangeFieldFocused(index))
                                        }
                                    },
                                    onNumberChanged = { newNumber ->
//                                onAction(VerifyIntent.OnEnterNumber(newNumber, index))
                                    },
                                    onKeyboardBack = {
//                                onAction(VerifyIntent.OnKeyboardBack)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                            }
                        }

                        TextView(
                            text = "Повторная отправка через 00:59",
                            modifier = Modifier.padding(top = 12.dp),
                            textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            ButtonView(
                text = "Отправить", textColor = Color.White,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 64.dp)
            ) { }

        }
    }
}

//@Preview
@Composable
private fun PreviewContent() {
    Content(
        focusRequesters = List(5) { FocusRequester() }
    )
}