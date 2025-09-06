package dev.soul.network

import kotlinx.serialization.Serializable

@Serializable
data class MainResponse<T>(
    val success: Boolean? = null,
    val error: String? = null,
    val data: T? = null,
    val message: String? = null,

    val detail: String? = null,
    val code: String? = null,
    val messages:List<Messages>? = emptyList(),
)

@Serializable
data class Messages(
    val token_class: String? = null,
    val token_type: String? = null,
    val message: String? = null,
)


//@Serializable
//data class ResponseData<T>(
//    val data: T,
//    val meta: PagingMetaData ?= null
//)
//
//@Serializable
//data class PagingResponse<T>(
//    val data: List<T>,
//    val meta: PagingMetaData
//)
