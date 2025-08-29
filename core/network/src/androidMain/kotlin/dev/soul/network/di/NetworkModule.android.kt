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
    return HttpClient(OkHttp) {
        Logger.log("dasdasd","${context.applicationInfo}")
        engine {
            addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250_000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )

        }
    }
}