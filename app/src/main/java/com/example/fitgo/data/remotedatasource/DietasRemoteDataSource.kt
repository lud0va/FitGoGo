package com.example.fitgo.data.remotedatasource

import com.example.common.Constantes
import com.example.fitgo.data.retrofit.calls.DietaApi
import com.example.fitgo.data.retrofit.calls.EjerciciosApi
import com.example.fitgo.domain.model.AlimentosPermitidos
import com.example.fitgo.domain.model.Dieta
import com.example.utils.NetworkResult
import com.squareup.moshi.Moshi
import javax.inject.Inject

class DietasRemoteDataSource @Inject constructor(
    private val  dietaApi: DietaApi,
    private val moshi: Moshi
)  {
    suspend fun getDietasByEmail(email:String): NetworkResult<List<Dieta>> {
        try {
            val response=
                dietaApi.getAllDietaByEmail(email)
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

    suspend fun getDieta(id:Int): NetworkResult<Dieta> {
        try {
            val response=
                dietaApi.getDieta(id)
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