package dev.soul.search

sealed interface SearchEvent {

    data class SearchQuery(val query: String) : SearchEvent
    data class IsSearchFocused(val active: Boolean) : SearchEvent

}