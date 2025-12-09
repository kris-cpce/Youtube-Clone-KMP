package org.company.app.di

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Android-specific HTTP client configuration that uses OkHttp engine with proper logging.
 *
 * CRITICAL FIX: The HttpLoggingInterceptor properly buffers response bodies, ensuring
 * that the Ktor client can read the full response. This prevents Content-Length: 0 issues.
 *
 * Previous Issue: Custom interceptors that consumed the response body before Ktor could read it
 */
actual fun HttpClientConfig<*>.setupHttpCache() {
    @Suppress("UNCHECKED_CAST")
    val okHttpConfig = this as HttpClientConfig<OkHttpConfig>
    okHttpConfig.engine {
        config {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }
    }
}
