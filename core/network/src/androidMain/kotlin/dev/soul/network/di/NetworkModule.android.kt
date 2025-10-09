package dev.soul.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dev.soul.shared.utils.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.java.KoinJavaComponent.get

actual fun createHttpClient(): HttpClient {
    val context: Context = get(Context::class.java)
    // Always use applicationContext to prevent memory leaks in singletons
    val appContext = context.applicationContext

    return HttpClient(OkHttp) {
        Logger.log("HttpClient", "${appContext.applicationInfo}")
        engine {
            addInterceptor(
                ChuckerInterceptor.Builder(appContext)
                    .collector(ChuckerCollector(appContext))
                    .maxContentLength(250_000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
        }
    }
}