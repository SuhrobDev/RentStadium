package dev.soul.auth.register_info.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.auth.register_info.RegisterEvent
import dev.soul.auth.register_info.RegisterState
import dev.soul.auth.register_info.RegisterViewModel
import dev.soul.shared.FontSize
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.BaseToolbar
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.CustomToast
import dev.soul.shared.components.NameView
import dev.soul.shared.components.PasswordView
import dev.soul.shared.components.TextView
import dev.soul.shared.components.ToastStatus
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.UiEvent

@Composable
fun RegisterInfoRoot(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    onBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigateHome()
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString())
                }

                is UiEvent.NavigateUp -> {
                    onBack()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CustomThemeManager.colors.screenBackground,
        topBar = {
            BaseToolbar(name = "", onBack = {
                onBack()
            })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Content
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
                        status = if (state.isVerified.not()) ToastStatus.ERROR else ToastStatus.INFO
                    )
                }
            )
        }

    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    BaseBox(backgroundColor = CustomThemeManager.colors.screenBackground) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
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

                Spacer(modifier = Modifier.height(24.dp))
                NameView(value = state.firstName, onValueChange = {
                    onEvent(RegisterEvent.FullNameChanged(it))
                })

                PasswordView(
                    password = state.password,
                    onPasswordChange = {
                        onEvent(RegisterEvent.PasswordChanged(it))
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )

                PasswordView(
                    password = state.confirmPassword,
                    onPasswordChange = {
                        onEvent(RegisterEvent.ConfirmPasswordChanged(it))
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }


            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.clip(RoundedCornerShape(12.dp))) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = {

                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = CustomThemeManager.colors.mainColor, // Green
                                uncheckedColor = CustomThemeManager.colors.lightGray, // Light gray
                                checkmarkColor = Color.White
                            ),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                    }

                    TextView(text = "Нажав на кнопку Далее, вы соглашаетесь с Правила использования Sport foot ")
                }

                ButtonView(
                    text = "Вход", textColor = Color.White,
                    isLoading = state.isLoading,
                    enabled = state.isVerified
                ) {
                    onEvent(RegisterEvent.Register)
                }

                TextView(text = "Уже есть аккаунт? Войти")
            }
        }
    }
}