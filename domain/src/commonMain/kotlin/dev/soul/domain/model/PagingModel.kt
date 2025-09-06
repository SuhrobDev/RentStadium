package dev.soul.domain.model

data class PagingModel<T>(
    val results: List<T>? = emptyList(),
    val count: Int? = null,
    val next: Int? = null,
    val previous: Int? = null
)
