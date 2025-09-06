package dev.soul.network

import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse<T>(
    val results: List<T>? = emptyList(),
    val count: Int? = null,
    val next: Int? = null,
    val previous: Int? = null
)
