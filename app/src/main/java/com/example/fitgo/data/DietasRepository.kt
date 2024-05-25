package com.example.fitgo.data

import com.example.fitgo.data.remotedatasource.DietasRemoteDataSource
import com.example.fitgo.domain.model.AlimentosPermitidos
import com.example.fitgo.domain.model.Dieta
import com.example.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DietasRepository @Inject constructor(
    private val dietasRemoteDataSource: DietasRemoteDataSource
)  {
    fun getDietasByEmail(email:String): Flow<NetworkResult<List<Dieta>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= dietasRemoteDataSource.getDietasByEmail(email)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getDieta(id:Int): Flow<NetworkResult<Dieta>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= dietasRemoteDataSource.getDieta(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}