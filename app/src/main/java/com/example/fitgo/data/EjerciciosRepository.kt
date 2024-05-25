package com.example.fitgo.data

import com.example.fitgo.data.remotedatasource.EjerciciosRemoteDataSource
import com.example.fitgo.data.remotedatasource.EntrenamientosRemoteDataSource
import com.example.fitgo.domain.model.Ejercicios
import com.example.fitgo.domain.model.Entrenamientos
import com.example.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EjerciciosRepository@Inject constructor(
    private val ejerciciosRemoteDataSource: EjerciciosRemoteDataSource
)   {
    fun getEjerciciosByEntrenamiento(id:Int): Flow<NetworkResult<List<Ejercicios>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= ejerciciosRemoteDataSource.getEjerciciosByEntrenamiento(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}