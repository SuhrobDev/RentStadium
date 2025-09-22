package dev.soul.auth.login.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.auth.login.LoginEvent
import dev.soul.auth.login.LoginState
import dev.soul.auth.login.LoginViewModel
import dev.soul.shared.FontSize
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.CustomToast
import dev.soul.shared.components.PasswordView
import dev.soul.shared.components.PhoneNumberInput
import dev.soul.shared.components.TextView
import dev.soul.shared.components.ToastStatus
import dev.soul.shared.mediumFont
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.UiEvent

@Composable
fun LoginRoot(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    if (event.route == Screen.RegPhone)
                        onNavigateRegister()
                    else
                        onNavigateHome()
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString())
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CustomThemeManager.colors.screenBackground
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Content(
                state = state,
                onEvent = viewModel::onEvent
            )

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp),
                snackbar = { data ->
                    CustomToast(
                        message = data.visuals.message,
                        onDismiss = {
                            snackbarHostState
                                .currentSnackbarData?.dismiss()
                        },
                        status = if (state.isLoggedIn.not()) ToastStatus.ERROR else ToastStatus.INFO
                    )
                }
            )
        }
    }
}

@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    BaseBox(
        backgroundColor = CustomThemeManager.colors.screenBackground
    ) {
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
                    fontSize = FontSize.LARGE,
                    fontWeight = FontWeight.Medium,
                    fontFamily = mediumFont()
                )

                TextView(
                    text = "Введите свой телефон номер для начала",
                    fontSize = FontSize.EXTRA_REGULAR,
                    textColor = CustomThemeManager.colors.textColor.copy(alpha = 0.6f)
                )

                PhoneNumberInput(
                    phone = state.phone,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    onEvent(LoginEvent.PhoneChanged(it))
                }

                PasswordView(
                    password = state.password,
                    onPasswordChange = {
                        onEvent(LoginEvent.PasswordChanged(it))
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Column(
                modifier = Modifier.padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ButtonView(
                    text = "Вход",
                    textColor = Color.White,
                    enabled = state.enableLogin,
                    isLoading = state.isLoading
                ) {
                    onEvent(LoginEvent.Login)
                }

                TextView(
                    text = "Нет аккаунта? Зарегистрируйтесь",
                    modifier = Modifier.clickable {
                        onEvent(LoginEvent.Register)
                    })
            }
        }
    }
}