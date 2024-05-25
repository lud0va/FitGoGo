package com.example.fitgo.data

import com.example.fitgo.data.remotedatasource.AlimentosPermitidosRemoteDataSource
import com.example.fitgo.data.remotedatasource.EjerciciosRemoteDataSource
import com.example.fitgo.domain.model.AlimentosPermitidos
import com.example.fitgo.domain.model.Ejercicios
import com.example.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AlimentosPermitidosRepository @Inject constructor(
    private val alimentosPermitidosRemoteDataSource: AlimentosPermitidosRemoteDataSource
)  {
    fun getAlimentosPermitido(id:Int): Flow<NetworkResult<List<AlimentosPermitidos>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= alimentosPermitidosRemoteDataSource.getAlimentosPermitidos(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}