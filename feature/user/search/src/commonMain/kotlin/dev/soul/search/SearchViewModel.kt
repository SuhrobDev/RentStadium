package dev.soul.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.user.search.StadiumOptions
import dev.soul.domain.repository.user.SearchRepository
import dev.soul.shared.Resources
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.NetworkError
import dev.soul.shared.utils.UiEvent
import dev.soul.shared.utils.onError
import dev.soul.shared.utils.onSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        setupDefaults()
        observeSearchQuery(_state.value.searchQuery)
    }

    private fun setupDefaults() {
        if (_state.value.options.isEmpty()) {
            _state.update {
                it.copy(
                    options = listOf(
                        StadiumOptions(
                            id = "1",
                            icon = "Resources.Image.Location",
                            title = "На карте",
                            screen = Screen.MapSearch
                        ),
                        StadiumOptions(
                            id = "2",
                            icon = "Resources.Image.Stadium",
                            title = "По стадионы",
                            screen = Screen.ByStadium
                        ),
                        StadiumOptions(
                            id = "3",
                            icon = "Resources.Image.Saved",
                            title = "По сохраненным",
                            screen = Screen.BySaved
                        ),
                    )
                )
            }
        }

        if (_state.value.types.isEmpty()) {
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
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchQuery -> {
                _state.update { it.copy(searchQuery = event.query) }
                observeSearchQuery(event.query)
            }

            is SearchEvent.IsSearchFocused -> {
                _state.update { it.copy(isSearchFocused = event.active) }
            }
        }
    }

    private var searchJob: Job? = null

    fun observeSearchQuery(search: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(1000)
            if (search.isNotBlank()) {
                _state.update { it.copy(isLoading = true) }

                repository.searchStadium(
                    page = 1,
                    size = 20,
                    name = search,
                    lat = 41.329907,
                    lng = 69.285253,
                    type = null,
                    maxPrice = null,
                    minPrice = null,
                    maxRate = null,
                    minRate = null
                ).collect { result ->
                    result.onSuccess { data ->
                        _state.update {
                            it.copy(
                                searchResults = data?.results ?: emptyList(),
                                isLoading = false,
                                error = null
                            )
                        }
                    }.onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = error?.message ?: NetworkError.UNKNOWN_ERROR.name
                            )
                        }
                    }
                }
            }
        }
    }
}