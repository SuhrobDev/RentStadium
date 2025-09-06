package dev.soul.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.repository.user.SearchRepository
import dev.soul.shared.Resources
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.UiEvent
import dev.soul.shared.utils.onError
import dev.soul.shared.utils.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        if (_state.value.options.isEmpty())
            _state.update {
                it.copy(
                    options = listOf(
                        Pair(Resources.Image.Location, "На карте"),
                        Pair(Resources.Image.Stadium, "По стадионы"),
                        Pair(Resources.Image.Saved, "По сохраненным"),
                    )
                )
            }

        if (_state.value.types.isEmpty())
            _state.update {
                it.copy(
                    types = listOf(
                        Triple(Resources.Image.StadiumType, "Открытый стадион", "123 та стадионы"),
                        Triple(
                            Resources.Image.ClosedStadium,
                            "Закрытый стадион",
                            "123 та стадионы"
                        ),
                    )
                )
            }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchQuery -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
//                searchUser(event.query)
            }

            is SearchEvent.IsSearchFocused -> {
                _state.update {
                    it.copy(
                        isSearchFocused = event.active
                    )
                }
            }
        }
    }

    private fun loadInitialGifts(limit: Int = 30) {
        val current = _state.value
        if (current.isInitialLoading) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isInitialLoading = true,
                    hasError = false,
                    error = null
                )
            }

            repository.searchStadium(
                page = 1,
                size = limit,
                name = _state.value.searchQuery,
                lat = 41.3517476,
                lng = 69.3015978,
                type = null,
                minRate = null,
                minPrice = null,
                maxRate = null,
                maxPrice = null
            ).collectLatest { result ->

                result
                    .onSuccess { response ->
                        val totalPages = ((response?.count ?: 0) + limit - 1) / limit
                        val currentPage = when {
                            response?.next != null -> (response.next ?: 1) - 1
                            response?.previous != null -> (response.previous ?: 0) + 1
                            else -> 1
                        }
                        val endReached = response?.next == null

                        _state.update {
                            it.copy(
                                isInitialLoading = false,
                                hasError = false,
                                error = null,
                                stadiums = response?.results ?: emptyList(),
                                currentPage = currentPage,
                                totalPages = totalPages,
                                endReached = endReached
                            )
                        }

                        canLoadMore()
                    }

                result.onError { error ->
                    _state.update {
                        it.copy(
                            isInitialLoading = false,
                            hasError = true,
                            error = error.toString()
                        )
                    }
                }
            }
        }
    }

    private fun loadMoreGifts(limit: Int = 4) {
        val current = _state.value

        if (current.isGiftLoading || current.endReached || current.hasError) {
            return
        }

        val nextPage = current.currentPage + 1

        if (nextPage > current.totalPages && current.totalPages > 0) {

            _state.update { it.copy(endReached = true) }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(isGiftLoading = true)
            }

            repository.searchStadium(
                page = 1,
                size = limit,
                name = _state.value.searchQuery,
                lat = 41.3517476,
                lng = 69.3015978,
                type = null,
                minRate = null,
                minPrice = null,
                maxRate = null,
                maxPrice = null
            ).collect { result ->
                result
                    .onSuccess { response ->
                        val totalPages = ((response?.count ?: 0) + limit - 1) / limit
                        val currentPage = when {
                            response?.next != null -> (response.next ?: 1) - 1
                            response?.previous != null -> (response.previous ?: 0) + 1
                            else -> 1
                        }
                        val endReached = response?.next == null

                        _state.update {
                            it.copy(
                                stadiums = it.stadiums + (response?.results ?: emptyList()),
                                isGiftLoading = false,
                                currentPage = currentPage,
                                totalPages = totalPages,
                                endReached = endReached,
                                hasError = false,
                                error = null
                            )
                        }

                        canLoadMore()
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isGiftLoading = false,
                                hasError = true,
                                error = error.toString()
                            )
                        }
                    }
            }
        }
    }

    private fun refreshGifts(limit: Int = 4) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    stadiums = emptyList(),
                    currentPage = 1,
                    totalPages = 0,
                    endReached = false,
                    hasError = false,
                    error = null
                )
            }
            loadInitialGifts(limit)
            canLoadMore()
        }
    }

    // Helper function to check if we can load more
    fun canLoadMore(): Boolean {
        val current = _state.value

        _state.update {
            it.copy(
                canLoadMore = !current.isGiftLoading &&
                        !current.endReached &&
                        !current.hasError &&
                        current.stadiums.isNotEmpty()
            )
        }
        return !current.isGiftLoading &&
                !current.endReached &&
                !current.hasError &&
                current.stadiums.isNotEmpty()
    }

    // Helper function to get loading state for UI
    fun isLoading(): Boolean {
        val current = _state.value
        return current.isInitialLoading || current.isGiftLoading
    }
}