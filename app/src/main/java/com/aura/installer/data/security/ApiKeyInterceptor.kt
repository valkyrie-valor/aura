package com.aura.installer.data.security

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor(
    private val apiKeyStore: ApiKeyStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val key = apiKeyStore.getApiKey()
        val request = if (key != null) {
            chain.request().newBuilder()
                .addHeader("X-Api-Key", key)
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}
