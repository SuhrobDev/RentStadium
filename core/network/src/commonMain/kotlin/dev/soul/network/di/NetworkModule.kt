package dev.soul.network.di

import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.datastore.datastore.PreferencesKeys
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LoggingConfig
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val TAG = "NetworkModule"
private const val base_url = "https://stadium.rent-home.uz/api/"
private const val REQUEST_TIMEOUT_MILLIS = 30_000L

/**
 * Provides network module configuration for dependency injection
 */
fun provideNetworkModule() = module {
    single {
        createHttpClient(get())
    }
}

/**
 * Creates and configures HttpClient with all necessary plugins
 */
private fun createHttpClient(userDataStore: DataStoreRepository): HttpClient {

    return createHttpClient().config {
        expectSuccess = false

        // Configure JSON serialization
        install(ContentNegotiation) {
            json(createJsonConfiguration())
        }

        install(DefaultRequest) {
            val language = runBlocking {
                userDataStore.getData(PreferencesKeys.LANGUAGE, "uz").first()
            }
            val accessToken = runBlocking {
                userDataStore.getData(PreferencesKeys.ACCESS_TOKEN, "").first()
            }
            configureDefaultRequest(language, accessToken)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIMEOUT_MILLIS
            socketTimeoutMillis = REQUEST_TIMEOUT_MILLIS
            connectTimeoutMillis = REQUEST_TIMEOUT_MILLIS
        }

        // Configure logging
        install(Logging) {
            configureLogging()
        }

        // Apply platform-specific configuration
//        configurePlatformSpecific()
    }
}

/**
 * Creates JSON configuration for serialization
 */
private fun createJsonConfiguration() = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

/**
 * Configures default request parameters
 */
private fun DefaultRequest.DefaultRequestBuilder.configureDefaultRequest(
    language: String,
    token: String
) {
    header("Accept-Language", language)
    header("Authorization", "Bearer $token")
    contentType(ContentType.Application.Json)
    accept(ContentType.Application.Json)
    url(base_url)
}

/**
 * Configures logging settings
 */
private fun LoggingConfig.configureLogging() {
    level = LogLevel.ALL
    logger = object : Logger {
        override fun log(message: String) {
            dev.soul.shared.utils.Logger.log(
                TAG,
                "HttpClient: $message"
            )
        }
    }
}

/**
 * Platform-specific HTTP client configuration
 * Implementation should be provided in platform-specific modules
 */
//expect fun HttpClient.Config.configurePlatformSpecific()

/**
 * Creates platform-specific HttpClient
 * This function should be implemented in platform-specific modules
 */
expect fun createHttpClient(): HttpClient
