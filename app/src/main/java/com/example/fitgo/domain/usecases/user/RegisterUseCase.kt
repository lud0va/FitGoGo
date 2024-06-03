package com.example.fitgo.domain.usecases.user

import com.example.fitgo.data.UserRepository
import com.example.utils.DataStoreTokens
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    val userRepository: UserRepository){
   fun invoke(email:String,password:String,username:String,code:String,sex:String,age:Int,weight:Int)=userRepository.doRegister(email,password, username,code,sex,age,weight)
}