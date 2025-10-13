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
                if (event.available.isActive) {
                    _state.update { currentState ->
                        val currentSelected = currentState.selectedAvailable
                        val newSelected = if (currentSelected.any { it.id == event.available.id }) {
                            currentSelected.filter { it.id != event.available.id }
                        } else {
                            currentSelected + event.available
                        }
                        currentState.copy(
                            selectedAvailable = newSelected
                        )
                    }
                }
            }

            is StadiumDetailEvent.SelectedWeekTab -> {
                _state.update {
                    it.copy(
                        selectedDate = state.value.upcomingDays.indexOfFirst { it.weekday.lowercase() == event.dayOfWeek.lowercase() }
                    )
                }
            }

            is StadiumDetailEvent.Book -> {
                book()
            }

            is StadiumDetailEvent.ScheduleDetail -> {
                getScheduleDetail(event.id)
            }

            is StadiumDetailEvent.DeleteSchedule -> {
                deleteSchedule(event.id)
            }
        }
    }

    init {
        upcomingDays()
    }

    private fun deleteSchedule(
        id: Int
    ) {
        viewModelScope.launch {
            repository.deleteSchedule(id).onStart {
                _state.update {
                    it.copy(
                        deleteLoading = true
                    )
                }
            }.collect {
                it.onSuccess { data ->
                    _state.update {
                        it.copy(
                            deleteLoading = false
                        )
                    }
                    _uiEvent.send(UiEvent.NavigateUp)
                }.onError { error ->
                    _state.update {
                        it.copy(
                            deleteLoading = false,
                            success = false,
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


    private fun getScheduleDetail(
        id: Int
    ) {
        viewModelScope.launch {
            repository.scheduleDetail(id).onStart {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }.collect {
                it.onSuccess { data ->
                    _state.update {
                        it.copy(
                            stadiumDetail = data?.stadium,
                            scheduleDetail = data,
                            isLoading = false
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            success = false,
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
                            success = false,
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
                            success = false,
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
                            success = false,
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

    private fun book() {
        viewModelScope.launch {
            val slots = state.value.selectedAvailable
            if (slots.isEmpty()) return@launch

            val stadiumId = state.value.stadiumDetail?.id ?: return@launch
            val upcomingDays = state.value.upcomingDays

            val bookRequests = slots.mapNotNull { slot ->
                // Find the date that corresponds to this slot's day of week
                val matchingDay = upcomingDays.find {
                    it.weekday.lowercase() == slot.dayOfWeekDisplay.lowercase()
                }

                matchingDay?.let { day ->
                    dev.soul.domain.model.user.book.request.BookRequestModel(
                        datetimeRange = dev.soul.domain.model.user.schedule.response.DatetimeRangeModel(
                            lower = "${day.date}T${slot.startTime}+05:00",
                            upper = "${day.date}T${slot.endTime}+05:00"
                        ),
                        note = "",
                        stadium = stadiumId,
                        status = "PENDING"
                    )
                }
            }

            if (bookRequests.isEmpty()) return@launch

            repository.book(bookRequests).onStart {
                _state.update {
                    it.copy(
                        bookLoading = true
                    )
                }
            }.collect { result ->
                result.onSuccess { data ->
                    _state.update { currentState ->
                        currentState.copy(
                            bookLoading = false,
                            success = true,
                            selectedAvailable = emptyList()
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                "Бронирование успешно создано!"
                            )
                        )
                    )
                }.onError { error ->
                    _state.update { currentState ->
                        currentState.copy(
                            success = false,
                            bookLoading = false,
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