package com.example.fitgo.data

import com.example.fitgo.data.model.AuthenticationResponse
import com.example.fitgo.data.remotedatasource.UserRemoteDataSource
import com.example.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class UserRepository  @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) {
    fun doLogin(mail:String,password:String):   Flow<NetworkResult<AuthenticationResponse>>{
        return flow {
            emit(NetworkResult.Loading())
            val result= userRemoteDataSource.getLogin(mail,password)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
    fun doRegister(mail:String,password:String,username:String,code:String,sex:String,age:Int,weigh:Int):Flow<NetworkResult<Boolean>>{
        return flow {
            emit(NetworkResult.Loading())
            val result=userRemoteDataSource.getRegister(mail,password,username,code,sex, age,weigh)
            emit(result)


        }.flowOn(Dispatchers.IO)
    }
}