package dev.soul.search.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.soul.domain.model.user.search.maps.StadiumModel
import dev.soul.domain.repository.user.SearchRepository
import dev.soul.shared.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapSearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(MapSearchState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            if (state.value.stadiums.isEmpty())
                _state.update {
                    it.copy(
                        stadiums = listOf(
                            StadiumModel(
                                id = "1",
                                lat = 41.3517476,
                                long = 69.3015978,
                                name = "Stadium 1",
                                address = "Address 1, Tashkent",
                                type = "Football",
                                price = 100.0,
                                rate = 4.5,
                                image = "Resources.Image.Stadium",
                                distance = 1.0,
                                isFavorite = false
                            ),
                            StadiumModel(
                                id = "2",
                                lat = 41.3520000,
                                long = 69.3020000,
                                name = "Stadium 2",
                                address = "Address 2, Tashkent",
                                type = "Mini-football",
                                price = 200.0,
                                rate = 4.0,
                                image = "Resources.Image.Stadium",
                                distance = 2.0,
                                isFavorite = true
                            ),
                            StadiumModel(
                                id = "3",
                                lat = 41.3530000,
                                long = 69.3030000,
                                name = "Arena Max",
                                address = "Yunusabad district",
                                type = "Indoor",
                                price = 150.0,
                                rate = 3.8,
                                image = "Resources.Image.Stadium",
                                distance = 3.5,
                                isFavorite = false
                            ),
                            StadiumModel(
                                id = "4",
                                lat = 41.3545000,
                                long = 69.3045000,
                                name = "Chilonzor Stadium",
                                address = "Chilonzor district",
                                type = "Outdoor",
                                price = 120.0,
                                rate = 4.2,
                                image = "Resources.Image.Stadium",
                                distance = 4.2,
                                isFavorite = false
                            ),
                            StadiumModel(
                                id = "5",
                                lat = 41.3560000,
                                long = 69.3055000,
                                name = "Olimpiya Sport Kompleksi",
                                address = "Mirzo Ulugbek district",
                                type = "Football",
                                price = 250.0,
                                rate = 4.9,
                                image = "Resources.Image.Stadium",
                                distance = 5.0,
                                isFavorite = true
                            )
                        )
                    )
                }
        }
    }

    fun onEvent(event: MapSearchEvent) {
        when (event) {
            is MapSearchEvent.GetStadiums -> {
                initial()
            }

            is MapSearchEvent.Back -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateUp)
                }
            }
        }
    }

    private fun initial() {
        viewModelScope.launch {

        }
    }
}