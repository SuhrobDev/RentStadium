package dev.soul.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.repository.user.ScheduleRepository
import dev.soul.shared.navigation.Screen
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

class ScheduleViewModel(
    private val repository: ScheduleRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(ScheduleState())
    val state = _state.asStateFlow()

    init {
        schedule()
    }

    fun onEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.AddFriend -> {

            }

            is ScheduleEvent.Refresh -> {
                schedule()
            }

            is ScheduleEvent.Detail -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.StadiumDetail(scheduleDetail = event.id)))
                }
            }
        }
    }

    private fun schedule() {
        viewModelScope.launch {
            repository.schedule(false).onStart {
                _state.update {
                    it.copy(isLoading = true)
                }
            }.collect {
                it.onSuccess { data ->
                    _state.update {
                        it.copy(isLoading = false, schedules = data ?: emptyList())
                    }
                }.onError { error ->
                    _state.update { it.copy(isLoading = false, success = false) }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(error?.message ?: error?.name ?: "")
                        )
                    )
                }
            }
        }
    }
}