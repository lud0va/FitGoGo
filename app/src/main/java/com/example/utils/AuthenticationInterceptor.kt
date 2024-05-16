package com.example.utils

import com.example.common.ConstantesServer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(private val dataStoreTokens: DataStoreTokens) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreTokens.getAccessToken().first()
        }
        val request = chain.request().newBuilder().header(ConstantesServer.AUTHORIZATION, "Bearer $token").build()
        return chain.proceed(request)
    }
}