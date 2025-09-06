package dev.soul.auth.otp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.auth.otp.VerifyIntent
import dev.soul.auth.otp.VerifyState
import dev.soul.auth.otp.VerifyViewModel
import dev.soul.shared.FontSize
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.BaseToolbar
import dev.soul.shared.components.ButtonView
import dev.soul.shared.components.CustomToast
import dev.soul.shared.components.OtpInputField22
import dev.soul.shared.components.TextView
import dev.soul.shared.components.ToastStatus
import dev.soul.shared.navigation.Screen
import dev.soul.shared.theme.CustomThemeManager
import dev.soul.shared.utils.UiEvent

@Composable
fun OtpRoot(
    modifier: Modifier = Modifier,
    viewModel: VerifyViewModel,
    onBack: () -> Unit,
    onNavigateRegister: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    val route = event.route as Screen.Register
                    onNavigateRegister(route.phone)
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
            Content(
                focusRequesters = List(5) { FocusRequester() },
                state = state,
                onEvent = viewModel::onAction
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
    focusRequesters: List<FocusRequester>,
    state: VerifyState = VerifyState(),
    onEvent: (VerifyIntent) -> Unit
) {
    BaseBox(backgroundColor = CustomThemeManager.colors.screenBackground) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {

                Column {
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
                            state.codes.forEachIndexed { index, number ->
                                OtpInputField22(
                                    number = number,
                                    focusRequester = focusRequesters[index],
                                    onFocusChanged = { isFocused ->
                                        if (isFocused) {
                                            onEvent(VerifyIntent.OnChangeFieldFocused(index))
                                        }
                                    },
                                    onNumberChanged = { newNumber ->
                                        onEvent(VerifyIntent.OnEnterNumber(newNumber, index))
                                    },
                                    onKeyboardBack = {
                                        onEvent(VerifyIntent.OnKeyboardBack)
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
                isLoading = state.isLoading,
                enabled = state.isValid == true,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 64.dp)
            ) {
                onEvent(VerifyIntent.OnVerifyOtp(state.codes.joinToString("") { it.toString() }))
            }

        }
    }
}