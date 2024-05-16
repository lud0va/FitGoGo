package com.example.utils

import com.example.common.ConstantesServer
import com.example.fitgo.data.model.AuthenticationResponse
import com.example.fitgo.data.retrofit.calls.UserApi
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticatior  constructor(
    private val dataStoreTokens: DataStoreTokens,
): Authenticator {
    val gson = GsonBuilder().setLenient().create()

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            dataStoreTokens.getRefreshToken().first()
        }
        return runBlocking {
            val newToken = getNewAccessToken(token)
            if (newToken.isSuccessful) {
                val newAcess = newToken.body()
                if (newAcess != null) {
                    dataStoreTokens.saveAccessToken(newAcess)
                }
                response.request.newBuilder()
                    .header(ConstantesServer.AUTHORIZATION, "Bearer $newAcess").build()
            } else {
                null
            }

        }
    }

    private suspend fun getNewAccessToken(refreshToken: String?): retrofit2.Response<String> {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ConstantesServer.IPSERVIDORAUTH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        val service = retrofit.create(UserApi::class.java)
        val response = service.getAccessToken("$refreshToken")

        return response

    }

}