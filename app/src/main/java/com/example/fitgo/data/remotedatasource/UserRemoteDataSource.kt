package com.example.fitgo.data.remotedatasource

import com.example.common.Constantes
import com.example.fitgo.data.model.AuthenticationResponse
import com.example.fitgo.data.retrofit.calls.UserApi
import com.example.utils.NetworkResult
import com.squareup.moshi.Moshi
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userApi: UserApi,
    private val moshi:Moshi
) {

    suspend fun getLogin(mail: String, password: String): NetworkResult<AuthenticationResponse> {
        try {
            val response=
                    userApi.login(mail,password)
            if (response.isSuccessful){
                val body=   response.body()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
                val msgerror = response.errorBody()?.string() ?:Constantes.SPACE
                val adapter = moshi.adapter(Error::class.java)
                val error = adapter.fromJson(msgerror)
                return NetworkResult.Error(error?.message?:Constantes.SPACE)

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }

    }
    suspend fun getRegister(mail: String, password: String,username:String): NetworkResult<Boolean> {
        try {
            val response=
                userApi.register(mail,password,username)
            if (response.isSuccessful){
                val body=   response.body()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
                val msgerror = response.errorBody()?.string() ?:Constantes.SPACE
                val adapter = moshi.adapter(Error::class.java)
                val error = adapter.fromJson(msgerror)
                return NetworkResult.Error(error?.message?:Constantes.SPACE)

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }

    }
}