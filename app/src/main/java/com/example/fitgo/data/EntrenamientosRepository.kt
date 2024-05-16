package com.example.fitgo.data

import com.example.fitgo.data.model.AuthenticationResponse
import com.example.fitgo.data.remotedatasource.EntrenamientosRemoteDataSource
import com.example.fitgo.data.remotedatasource.UserRemoteDataSource
import com.example.fitgo.domain.model.Entrenamientos
import com.example.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EntrenamientosRepository @Inject constructor(
    private val entrenamientosRemoteDataSource: EntrenamientosRemoteDataSource
)  {
    fun getEntrenamientos(email:String): Flow<NetworkResult<List<Entrenamientos>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result= entrenamientosRemoteDataSource.getEntrenamientos(email)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}