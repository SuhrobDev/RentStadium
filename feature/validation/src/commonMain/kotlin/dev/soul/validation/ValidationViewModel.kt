package dev.soul.validation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.repository.UserRepository
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.UiEvent
import dev.soul.shared.utils.onError
import dev.soul.shared.utils.onSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ValidationViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(ValidationState())
    val state = _state.asStateFlow()

    private var validationJob: Job? = null   // track current validation flow

    init {
        startValidation()
    }

    fun onEvent(event: ValidationEvent) {
        when (event) {
            is ValidationEvent.ShowLogout -> {
                _state.update {
                    it.copy(shouldLogout = event.show)
                }
            }
        }
    }

    private fun startValidation() {
        validationJob?.cancel()
        validationJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getUser {
                _state.update { it.copy(isLoading = false) }
                launch { _uiEvent.send(UiEvent.Navigate(Screen.Login)) }
            }.collectLatest { res ->
                res.onSuccess { user ->
                    _uiEvent.send(UiEvent.Navigate(Screen.Base))
                }.onError { error ->
                    if (error?.name == "UNAUTHORIZED") {
                        refreshToken()
                    } else {
                        _uiEvent.send(UiEvent.Navigate(Screen.Login))
                    }
                }
            }
        }
    }

    private fun refreshToken() {
        viewModelScope.launch {
            repository.refreshToken().collectLatest { res ->
                res.onSuccess {
                    // restart validation cleanly
                    startValidation()
                }.onError {
                    _state.update {
                        it.copy(isLoading = false, shouldLogout = true)
                    }
                }
            }
        }
    }

//    private fun init() {
//        viewModelScope.launch {
//            _state.update {
//                it.copy(isLoading = true)
//            }
//            repository.getUser {
//                _state.update {
//                    it.copy(
//                        isLoading = false
//                    )
//                }
//                launch {
//                    _uiEvent.send(UiEvent.Navigate(Screen.Login))
//                }
//            }.collectLatest { res ->
//                res.onSuccess { user ->
//                    Logger.log("daljkwne","user: $user")
//                    _uiEvent.send(UiEvent.Navigate(Screen.Base))
//                }.onError { error ->
//                    if (error?.name == "UNAUTHORIZED")
//                        refreshToken()
//                    else
//                        _uiEvent.send(UiEvent.Navigate(Screen.Login))
//                }
//            }
//        }
//    }
//
//    private fun refreshToken() {
//        viewModelScope.launch {
//            repository.refreshToken().collectLatest { res ->
//                res.onSuccess {
//                    init()
//                }.onError {
//                    _state.update {
//                        it.copy(
//                            isLoading = false, shouldLogout = true
//                        )
//                    }
//                }
//            }
//        }
//    }
}