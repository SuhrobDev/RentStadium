package dev.soul.network

enum class NetworkError(
    var message: String? = null
) {
    NETWORK_ERROR,
    TIMEOUT_ERROR,
    NOT_FOUND,
    NO_INTERNET,
    SERVER_ERROR,
    SERVER_NOT_RESPONDING,
    UNAUTHORIZED,
    UNKNOWN,
    SERIALIZATION,
    REDIRECT,
    UNKNOWN_ERROR,
    NULL_BODY,
    SERIALIZATION_ERROR,
    SERVER_CUSTOM_ERROR;

    companion object {
        fun valueOf(errorMessage: String): NetworkError {
            return when (errorMessage.lowercase()) {
                "network_error", "network error" -> NETWORK_ERROR
                "timeout_error", "timeout error" -> TIMEOUT_ERROR
                "server_error", "server error" -> SERVER_ERROR
                "unauthorized" -> UNAUTHORIZED
                "not_found", "not found" -> NOT_FOUND
                "null body"-> NULL_BODY
                "serialization_error", "serialization error" -> SERIALIZATION_ERROR
                else -> UNKNOWN_ERROR
            }
        }
    }
}