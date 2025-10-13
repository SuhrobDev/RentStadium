package dev.soul.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.repository.user.LikeShareRepository
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.UiEvent
import dev.soul.shared.utils.UiText
import dev.soul.shared.utils.onError
import dev.soul.shared.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LikedViewModel(
    private val repository: LikeShareRepository
) : ViewModel() {
    private val PAGE_SIZE = 100

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(LikedState())
    val state = _state.asStateFlow()

    private val allLikedItems = mutableListOf<StadiumItemModel>()

    fun onEvent(event: LikedEvent) {
        when (event) {
            is LikedEvent.Liked -> {
                like(event.id, event.current)
            }

            is LikedEvent.More -> {
                initial()
            }

            is LikedEvent.Refresh -> {
                refresh()
            }

            is LikedEvent.Detail -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.StadiumDetail(detail = event.id)))
                }
            }
        }
    }

    init {
        initial()
    }

    private fun like(id: Int, current: Boolean) {
        viewModelScope.launch {
            if (current)
                repository.deleteLiked(id = id).collect { result ->
                    result.onSuccess { data ->
                        _state.update { state ->
                            state.copy(
                                success = true,
                                likedList = state.likedList.map { item ->
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
                repository.like(id = id).collect { result ->
                    result.onSuccess { data ->
                        _state.update { state ->
                            state.copy(
                                success = true,
                                likedList = state.likedList.map { item ->
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

    private fun initial() = viewModelScope.launch {
        repository.likes(page = 1, size = PAGE_SIZE, lat = 12.9721, lng = 77.5933).onStart {
            _state.update {
                it.copy(
                    isInitialLoading = true, likedList = emptyList()
                )
            }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allLikedItems.clear()
                allLikedItems.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        likedList = allLikedItems.toList(),
                        currentPage = 1,
                        hasNextPage = data?.next != null
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        isLoadingMore = false,
                        isRefreshing = false,
                    )
                }
            }
        }
    }

    private fun refresh() = viewModelScope.launch {
        repository.likes(
            page = 1, size = PAGE_SIZE, lat = 12.9721, lng = 77.5933
        ).onStart {
            _state.update { it.copy(isRefreshing = true) }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allLikedItems.clear()
                allLikedItems.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isRefreshing = false,
                        likedList = allLikedItems.toList(),
                        currentPage = 1,
                        hasNextPage = data?.next != null
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isRefreshing = false,
                    )
                }
            }
        }
    }

    private fun loadMore() = viewModelScope.launch {
        if (state.value.hasNextPage.not()) return@launch
        val nextPage = state.value.currentPage + 1
        repository.likes(
            page = nextPage, size = PAGE_SIZE, lat = 12.9721, lng = 77.5933
        ).onStart {
            _state.update {
                it.copy(
                    isLoadingMore = true
                )
            }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allLikedItems.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isLoadingMore = false,
                        likedList = allLikedItems.toList(),
                        currentPage = nextPage,
                        hasNextPage = data?.next != null
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoadingMore = false,
                    )
                }
            }
        }
    }
}