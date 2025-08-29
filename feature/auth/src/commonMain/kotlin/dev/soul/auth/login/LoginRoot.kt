package dev.soul.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.soul.shared.FontSize
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.PasswordView
import dev.soul.shared.components.PhoneNumberInput
import dev.soul.shared.components.TextView
import dev.soul.shared.theme.CustomThemeManager

@Composable
fun LoginRoot(
    modifier: Modifier = Modifier,
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit
) {
    Content()
}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    BaseBox {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(56.dp))

                TextView(
                    text = "Войти в аккаунт",
                    fontSize = FontSize.EXTRA_MEDIUM,
                    fontWeight = FontWeight.Medium
                )

                TextView(
                    text = "Введите свой телефон номер для начала",
                    modifier = Modifier.padding(top = 4.dp),
                    textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.6f)
                )

                PhoneNumberInput(phone = "", modifier = Modifier.padding(top = 24.dp)) { }

                PasswordView(
                    password = "",
                    onPasswordChange = {

                    },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Column(
                modifier = Modifier.padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ButtonView(text = "Вход", textColor = Color.White) { }

                TextView(text = "Нет аккаунта? Зарегистрируйтесь")
            }
        }
    }
}

//@Preview
@Composable
private fun PreviewContent() {
    Content()
}