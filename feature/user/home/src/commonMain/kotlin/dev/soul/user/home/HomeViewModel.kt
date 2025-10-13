package dev.soul.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.repository.user.HomeRepository
import dev.soul.domain.repository.user.LikeShareRepository
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.Logger
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

class HomeViewModel(
    private val repository: HomeRepository,
    private val likeShareRepository: LikeShareRepository
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Popular -> {
                popular()
            }

            is HomeEvent.Personalized -> {
                personalized()
            }

            is HomeEvent.Detail -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.StadiumDetail(detail =  event.id)))
                }
            }

            is HomeEvent.MorePopular -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.More(isPopular = true)))
                }
            }

            is HomeEvent.MorePersonalized -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.More(isPersonalized = true)))
                }
            }

            is HomeEvent.Like -> {
                like(event.id, event.current, event.isPopular)
            }

            is HomeEvent.Liked -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.Liked))
                }
            }
        }
    }

    init {
        personalized()
        popular()
    }

    private fun like(id: Int, current: Boolean, isPopular: Boolean) {
        viewModelScope.launch {
            if (current)
                likeShareRepository.deleteLiked(id = id).collect { result ->
                    result.onSuccess { data ->
                        _state.update { state ->
                            state.copy(
                                success = true,
                                popularList = state.popularList.map { item ->
                                    if (item.id == id) item.copy(liked = false) else item
                                },
                                personalizedList = state.personalizedList.map { item ->
                                    if (item.id == id) item.copy(liked = false) else item
                                }
                            )
                        }
                    }.onError { error ->
                        _state.update { it.copy(success = false) }
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                UiText.DynamicString(error?.message ?: error?.name ?: "")
                            )
                        )
                    }
                }
            else
                likeShareRepository.like(id = id).collect { result ->
                    result.onSuccess { data ->
                        _state.update { state ->
                            state.copy(
                                success = true,
                                popularList = state.popularList.map { item ->
                                    if (item.id == id) item.copy(liked = true) else item
                                },
                                personalizedList = state.personalizedList.map { item ->
                                    if (item.id == id) item.copy(liked = true) else item
                                }
                            )
                        }
                    }.onError { error ->
                        _state.update { it.copy(success = false) }
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                UiText.DynamicString(error?.message ?: error?.name ?: "")
                            )
                        )
                    }
                }
        }
    }

    private fun personalized() {
        viewModelScope.launch {
            repository.personalized().onStart {
                _state.update {
                    it.copy(personalizedLoading = true)
                }
            }.collect {
                it.onSuccess { data ->
                    Logger.log("dashewreqw", "$data")
                    _state.update {
                        it.copy(
                            personalizedList = data ?: emptyList(),
                            personalizedLoading = false
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(personalizedLoading = false, success = false)
                    }
                    Logger.log("dashewreqw", "${error?.message}")

                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                error?.message ?: error?.name ?: "Error on loading personalized"
                            )
                        )
                    )
                }
            }
        }
    }


    private fun popular() {
        viewModelScope.launch {
            repository.popular(
                12.9721,
                77.5933
            ).onStart {
                _state.update {
                    it.copy(popularLoading = true)
                }
            }.collect {
                it.onSuccess { data ->
                    _state.update {
                        it.copy(
                            popularList = data?.results ?: emptyList(),
                            popularLoading = false
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            popularLoading = false,
                            success = false
                        )
                    }
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                error?.message ?: error?.name ?: "Error on loading popular"
                            )
                        )
                    )
                }
            }
        }
    }

}