package com.example.fitgo.data.remotedatasource

import com.example.common.Constantes
import com.example.fitgo.data.retrofit.calls.EjerciciosApi
import com.example.fitgo.data.retrofit.calls.PlatosApi
import com.example.fitgo.domain.model.Dieta
import com.example.fitgo.domain.model.Plato
import com.example.utils.NetworkResult
import com.squareup.moshi.Moshi
import javax.inject.Inject

class PlatosRemoteDataSource @Inject constructor(
    private val  platosApi: PlatosApi,
    private val moshi: Moshi
) {
    suspend fun getPlatosDieta(id:Int): NetworkResult<List<Plato>> {
        try {
            val response=
                platosApi.getAllByDieta(id)
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