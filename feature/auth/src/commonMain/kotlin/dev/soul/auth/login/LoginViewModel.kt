package dev.soul.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.auth.login.request.LoginRequestModel
import dev.soul.domain.repository.auth.AuthRepository
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.UiEvent
import dev.soul.shared.utils.UiText
import dev.soul.shared.utils.onError
import dev.soul.shared.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PhoneChanged -> {
                Logger.log("jnqjwe", "PhoneChanged: ${event.phone.replace(" ", "").length}")
                _state.update {
                    it.copy(
                        phone = event.phone,
                        enableLogin = event.phone.replace(
                            " ",
                            ""
                        ).length == 13 && _state.value.password.length >= 8
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        enableLogin = _state.value.phone.replace(
                            " ",
                            ""
                        ).length == 13 && event.password.length >= 8
                    )
                }
            }

            is LoginEvent.Login -> {
                login()
            }

            is LoginEvent.Register -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.RegPhone))
                }
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            repository.login(
                LoginRequestModel(
                    _state.value.phone.replace(" ", ""),
                    _state.value.password.replace(" ", "")
                )
            ).onStart {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }.onEach { b ->
                b.onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true
                        )
                    }
                    _uiEvent.send(UiEvent.Navigate(Screen.Base))
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            message = UiText.DynamicString(
                                error?.message ?: error?.name ?: ""
                            )
                        )
                    )

                    _uiEvent.send(UiEvent.Navigate(Screen.RegPhone))

                }
            }.launchIn(viewModelScope)

        }
    }
}