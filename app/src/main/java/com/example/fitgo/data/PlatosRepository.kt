package com.example.fitgo.data

import com.example.fitgo.data.remotedatasource.EjerciciosRemoteDataSource
import com.example.fitgo.data.remotedatasource.PlatosRemoteDataSource
import com.example.fitgo.domain.model.Dieta
import com.example.fitgo.domain.model.Plato
import com.example.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlatosRepository @Inject constructor(
    private val  platosRemoteDataSource: PlatosRemoteDataSource
)  {
    fun getPlatosByDieta(id:Int): Flow<NetworkResult<List<Plato>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= platosRemoteDataSource.getPlatosDieta(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}