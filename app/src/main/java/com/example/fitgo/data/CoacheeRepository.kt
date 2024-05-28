package com.example.fitgo.data

import com.example.fitgo.data.remotedatasource.CoacheeRemoteDataSource
import com.example.fitgo.data.remotedatasource.DietasRemoteDataSource
import com.example.fitgo.domain.model.Coachee
import com.example.fitgo.domain.model.Dieta
import com.example.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CoacheeRepository @Inject constructor(
    private val coacheeRemoteDataSource: CoacheeRemoteDataSource
)   {
    fun getCoacheeByEmail(email:String):Flow<NetworkResult<Coachee>>  {
        return flow {
            emit(NetworkResult.Loading())
            val result= coacheeRemoteDataSource.getCoacheeByEmail(email)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}