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

            is StadiumDetailEvent.DateSelect -> {
                _state.update {
                    it.copy(
                        selectedDate = event.date
                    )
                }
                available(_state.value.upcomingDays[event.date].date)
            }

            is StadiumDetailEvent.AvailableSelect -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            selectedAvailable = event.available
                        )
                    }
                }
            }
        }
    }

    init {
        upcomingDays()
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

    private fun upcomingDays() {
        viewModelScope.launch {
            repository.upcomingDays().onStart {
                _state.update {
                    it.copy(
                        dateLoading = true
                    )
                }
            }.collect {
                it.onSuccess { data ->
                    _state.update {
                        it.copy(
                            selectedDate = 0,
                            upcomingDays = data ?: emptyList(),
                            dateLoading = false
                        )
                    }
                    if (data?.isNotEmpty() == true)
                        available(data[0].date)

                }.onError { error ->
                    _state.update {
                        it.copy(
                            dateLoading = false,
                            error = it.error
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

    private fun available(date: String) {
        viewModelScope.launch {
            val id = state.value.stadiumDetail?.id ?: return@launch
            repository.available(id, date).onStart {
                _state.update {
                    it.copy(
                        availableLoading = true
                    )
                }
            }.collect {
                it.onSuccess { data ->
                    _state.update {
                        it.copy(
                            availableLoading = false,
                            available = data ?: emptyList()
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            dateLoading = false,
                            error = it.error
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