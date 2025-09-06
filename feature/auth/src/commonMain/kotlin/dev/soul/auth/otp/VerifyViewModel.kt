package dev.soul.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.auth.verify.request.VerifyOtpModel
import dev.soul.domain.repository.auth.AuthRepository
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.UiEvent
import dev.soul.shared.utils.UiText
import dev.soul.shared.utils.onError
import dev.soul.shared.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerifyViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(VerifyState())
    val state = _state.asStateFlow()

    fun onAction(action: VerifyIntent) {
        when (action) {
            is VerifyIntent.OnChangeFieldFocused -> {
                _state.update {
                    it.copy(
                        focusedIndex = action.index
                    )
                }
            }

            is VerifyIntent.OnEnterNumber -> {
                Logger.log("OnEnterNumber", "onEnterNumber: ${action.number}")
                enterNumber(action.number, action.index)
            }

            is VerifyIntent.OnVerified -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.Register(
                        _state.value.phone
                    )))
                }
            }

            is VerifyIntent.OnRestartTimer -> {
                startTimer()
            }

            is VerifyIntent.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex)
                _state.update {
                    it.copy(
                        codes = it.codes.mapIndexed { index, number ->
                            if (index == previousIndex) {
                                null
                            } else {
                                number
                            }
                        },
                        focusedIndex = previousIndex
                    )
                }
            }


            is VerifyIntent.OnVerifyOtp -> {
                verifyOtp(action.otp)
            }

            is VerifyIntent.OnResendOtp -> {
                if (_state.value.isRunning.not())
                    startTimer()
            }

            is VerifyIntent.OnGetOtp -> {
                _state.update {
                    it.copy(phone = action.phoneNumber.replace(" ", ""))
                }
            }

            is VerifyIntent.ClearState -> {
                _state.update {
                    it.copy(
                        error = null
                    )
                }
            }

            is VerifyIntent.Back -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateUp)
                }
            }
        }
    }

    private fun verifyOtp(code: String) {
        viewModelScope.launch {
            authRepository.verifyOtp(
                VerifyOtpModel(
                    phoneNumber = _state.value.phone,
                    otp = code
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
                    _uiEvent.send(UiEvent.Navigate(Screen.Register(
                        _state.value.phone
                    )))
                }
                it.onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error?.message,
                            isVerified = false
                        )
                    }

                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                error?.message ?: "Error"
                            )
                        )
                    )

                }
            }.launchIn(viewModelScope)
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode = state.value.codes.mapIndexed { currentIndex, currentNumber ->
            if (currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null

        Logger.log("enterNumber", "wasNumberRemoved: $wasNumberRemoved")
        Logger.log("enterNumber", "newCode: $newCode")

        _state.update {
            it.copy(
                codes = newCode,
                focusedIndex = if (wasNumberRemoved || it.code.getOrNull(index) != null) {
                    it.focusedIndex
                } else {
                    getNextFocusedTextFieldIndex(
                        currentCode = it.codes,
                        currentFocusedIndex = it.focusedIndex
                    )
                },
                isValid = newCode.size == 5 && newCode.none { it == null }
            )
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if (currentFocusedIndex == null) {
            return null
        }

        if (currentFocusedIndex == 4) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if (index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if (number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }

    private fun startTimer() {
        _state.update {
            it.copy(
                timeLeft = it.totalTimeMillis,
                isRunning = true
            )
        }

        viewModelScope.launch {
            while (_state.value.timeLeft > 0) {
                delay(1000L)
                _state.update {
                    it.copy(
                        timeLeft = _state.value.timeLeft - 1000L,
                    )
                }
            }
            _state.update {
                it.copy(
                    isRunning = false,
                )
            }
        }
    }

}