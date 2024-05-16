package com.example.fitgo.data.remotedatasource

import com.example.common.Constantes
import com.example.fitgo.data.retrofit.calls.EntrenamientosApi
import com.example.fitgo.data.retrofit.calls.UserApi
import com.example.fitgo.domain.model.Entrenamientos
import com.example.utils.NetworkResult
import com.squareup.moshi.Moshi
import javax.inject.Inject

class EntrenamientosRemoteDataSource @Inject constructor(
    private val  entrenamientosApi: EntrenamientosApi,
    private val moshi: Moshi
) {
    suspend fun getEntrenamientos(email:String):NetworkResult<List<Entrenamientos>>{
        try {
            val response=
                entrenamientosApi.getAllEntrenamientos(email)
            if (response.isSuccessful){
                val body=
                    response.body()
                body?.let {
                    return NetworkResult.Success(it)
                }
                error(Constantes.NO_DATA)
            } else {
                val msgerror = response.errorBody()?.string() ?: Constantes.SPACE
                val adapter = moshi.adapter(Error::class.java)
                val error = adapter.fromJson(msgerror)
                return NetworkResult.Error(error?.message?: Constantes.SPACE)

            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }

    }
}