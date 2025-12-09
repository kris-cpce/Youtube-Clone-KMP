package org.company.app.di

import io.ktor.client.HttpClientConfig

expect fun HttpClientConfig<*>.setupHttpCache()

