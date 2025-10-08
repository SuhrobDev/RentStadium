package dev.soul.stadium_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.repository.user.StadiumDetailRepository
import dev.soul.shared.utils.UiEvent
import dev.soul.shared.utils.UiText
import dev.soul.shared.utils.onError
import dev.soul.shared.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StadiumDetailViewModel(
    private val repository: StadiumDetailRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(StadiumDetailState())
    val state = _state.asStateFlow()

    fun onEvent(event: StadiumDetailEvent) {
        when (event) {
            is StadiumDetailEvent.Detail -> {
                getDetail(event.id)
            }
            is StadiumDetailEvent.Share -> {
                viewModelScope.launch {
                }
            }
        }
    }

    private fun getDetail(
        id: Int
    ) {
        viewModelScope.launch {
            repository.stadiumDetail(id).onStart {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }.collect {
                it.onSuccess { data ->
                    _state.update {
                        it.copy(
                            stadiumDetail = data,
                            isLoading = false
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error?.message ?: error?.name ?: "Unknown error"
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                error?.message ?: error?.name ?: "Unknown error"
                            )
                        )
                    )
                }
            }
        }
    }
}