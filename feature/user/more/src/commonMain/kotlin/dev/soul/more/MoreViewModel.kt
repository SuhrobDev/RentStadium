package dev.soul.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.repository.user.LikeShareRepository
import dev.soul.domain.repository.user.MoreRepository
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

class MoreViewModel(
    private val repository: MoreRepository,
    private val likeShareRepository: LikeShareRepository
) : ViewModel() {
    private val PAGE_SIZE = 100

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(MoreState())
    val state = _state.asStateFlow()

    private val allPopularItem = mutableListOf<StadiumItemModel>()
    private val allPersonalizedItem = mutableListOf<StadiumItemModel>()

    fun onEvent(event: MoreEvent) {
        when (event) {
            is MoreEvent.Detail -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.StadiumDetail(detail = event.id)))
                }
            }

            is MoreEvent.Type -> {
                when (event.type) {
                    MoreType.POPULAR -> {
                        _state.update {
                            it.copy(
                                isPopular = true, isPersonalized = false
                            )
                        }
                        initialPopular()
                    }

                    MoreType.PERSONALIZED -> {
                        _state.update {
                            it.copy(
                                isPopular = false, isPersonalized = true
                            )
                        }
                        initialPersonalized()
                    }
                }
            }

            is MoreEvent.onLoadMore -> {
                if (state.value.isPopular == true) {
                    loadMorePopular()
                } else {
                    loadMorePersonalized()
                }
            }

            is MoreEvent.onRefresh -> {
                if (state.value.isPopular == true) {
                    refreshHPopular()
                } else {
                    refreshPersonalized()
                }
            }

            is MoreEvent.Like -> {
                like(event.id, event.current, event.isPopular)
            }
        }
    }

    private fun like(id: Int, current: Boolean, isPopular: Boolean) {
        viewModelScope.launch {
            if (current)
                likeShareRepository.deleteLiked(id = id).collect { result ->
                    result.onSuccess { data ->
                        _state.update { state ->
                            if (isPopular) {
                                state.copy(
                                    success = true,
                                    popularList = state.popularList.map { item ->
                                        if (item.id == id) item.copy(liked = false) else item
                                    }
                                )
                            } else {
                                state.copy(
                                    success = true,
                                    personalizedList = state.personalizedList.map { item ->
                                        if (item.id == id) item.copy(liked = false) else item
                                    }
                                )
                            }
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
                            if (isPopular) {
                                state.copy(
                                    success = true,
                                    popularList = state.popularList.map { item ->
                                        if (item.id == id) item.copy(liked = true) else item
                                    }
                                )
                            } else {
                                state.copy(
                                    success = true,
                                    personalizedList = state.personalizedList.map { item ->
                                        if (item.id == id) item.copy(liked = true) else item
                                    }
                                )
                            }
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


    private fun initialPopular() = viewModelScope.launch {
        repository.popular(page = 1, size = PAGE_SIZE, lat = 12.9721, lng = 77.5933).onStart {
            _state.update {
                it.copy(
                    isInitialLoading = true, popularList = emptyList()
                )
            }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allPopularItem.clear()
                allPopularItem.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        popularList = allPopularItem.toList(),
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

    private fun initialPersonalized() = viewModelScope.launch {
        repository.personalized(page = 1, size = PAGE_SIZE).onStart {
            _state.update {
                it.copy(
                    isInitialLoading = true, personalizedList = emptyList()
                )
            }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allPersonalizedItem.clear()
                allPersonalizedItem.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isInitialLoading = false,
                        personalizedList = allPersonalizedItem.toList(),
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

    private fun refreshHPopular() = viewModelScope.launch {
        repository.popular(
            page = 1, size = PAGE_SIZE, lat = 12.9721, lng = 77.5933
        ).onStart {
            _state.update { it.copy(isRefreshing = true) }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allPopularItem.clear()
                allPopularItem.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isRefreshing = false,
                        popularList = allPopularItem.toList(),
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

    private fun refreshPersonalized() = viewModelScope.launch {
        repository.personalized(
            page = 1, size = PAGE_SIZE,
        ).onStart {
            _state.update { it.copy(isRefreshing = true) }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allPersonalizedItem.clear()
                allPersonalizedItem.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isRefreshing = false,
                        personalizedList = allPersonalizedItem.toList(),
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

    private fun loadMorePopular() = viewModelScope.launch {
        if (state.value.hasNextPage.not()) return@launch
        val nextPage = state.value.currentPage + 1
        repository.popular(
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
                allPopularItem.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isLoadingMore = false,
                        popularList = allPopularItem.toList(),
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

    private fun loadMorePersonalized() = viewModelScope.launch {
        if (state.value.hasNextPage.not()) return@launch
        val nextPage = state.value.currentPage + 1
        repository.personalized(
            page = nextPage,
            size = PAGE_SIZE,
        ).onStart {
            _state.update {
                it.copy(
                    isLoadingMore = true
                )
            }
        }.collectLatest { res ->
            res.onSuccess { data ->
                val newItems = data?.results ?: emptyList()
                allPersonalizedItem.addAll(newItems.toList())

                _state.update {
                    it.copy(
                        isLoadingMore = false,
                        personalizedList = allPersonalizedItem.toList(),
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