package dev.soul.auth.register_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.auth.register.request.RegisterModel
import dev.soul.domain.repository.auth.AuthRepository
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.UiEvent
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

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.PhoneChanged -> {
                _state.update {
                    it.copy(phone = event.phone)
                }
                isValid()
            }

            is RegisterEvent.FullNameChanged -> {
                _state.update {
                    it.copy(firstName = event.firstName)
                }
                isValid()
            }

            is RegisterEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
                isValid()
            }

            is RegisterEvent.ConfirmPasswordChanged -> {
                _state.update {
                    it.copy(confirmPassword = event.confirmPassword)
                }
                isValid()
            }

            is RegisterEvent.Register -> {
                register()
            }

        }
    }

    private fun register() {
        viewModelScope.launch {
            authRepository.register(
                RegisterModel(
                    phoneNumber = state.value.phone,
                    firstName = state.value.firstName,
                    password = state.value.password,
                    repeatPassword = state.value.confirmPassword,
                    lastName = ""
                )
            ).onStart {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }.onEach {
                it.onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isVerified = true
                        )
                    }
                    _uiEvent.send(UiEvent.Navigate(Screen.Base))
                }

                it.onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error?.message,
                            isVerified = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun isValid(){
        val phone = state.value.phone
        val fullName = state.value.firstName
        val password = state.value.password
        val confirmPassword = state.value.confirmPassword

        val hasError = listOf(
            phone.isNotBlank() && phone.length == 13,
            fullName.isNotBlank() && fullName.length >= 3,
            password.isNotBlank() && password.length >= 6,
            confirmPassword == password ,
        ).all { it }

        _state.update {
            it.copy(
                isVerified = hasError
            )
        }
    }
}