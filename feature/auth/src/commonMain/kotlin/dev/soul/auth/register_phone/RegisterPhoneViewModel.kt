package dev.soul.auth.register_phone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.auth.phone.request.RegPhoneModel
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

class RegisterPhoneViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterPhoneState())
    val state = _state.asStateFlow()


    fun onEvent(event: RegisterPhoneEvent) {
        when (event) {
            is RegisterPhoneEvent.PhoneChanged -> {
                Logger.log("jnqjwe", "PhoneChanged: ${event.phone.replace(" ", "").length}")
                _state.update {
                    it.copy(
                        phone = event.phone,
                        enableButton = event.phone.replace(
                            " ",
                            ""
                        ).length == 13
                    )
                }
            }

            is RegisterPhoneEvent.Register -> {
                register()
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            repository.getOtp(
                RegPhoneModel(
                    _state.value.phone.replace(" ", "")
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
                            isSuccess = true
                        )
                    }
                    _uiEvent.send(
                        UiEvent.Navigate(
                            Screen.VerifyCode(
                                _state.value.phone.replace(
                                    " ",
                                    ""
                                )
                            )
                        )
                    )
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            message = UiText.DynamicString(
                                error?.message ?: error?.name ?: ""
                            )
                        )
                    )

                }
            }.launchIn(viewModelScope)

        }
    }
}