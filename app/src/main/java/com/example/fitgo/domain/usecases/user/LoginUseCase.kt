package com.example.fitgo.domain.usecases.user

import android.content.SharedPreferences
import com.example.fitgo.data.UserRepository
import com.example.fitgo.data.model.AuthenticationResponse
import com.example.utils.DataStoreTokens
import com.example.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    val userRepository: UserRepository,
    val  dataStoreTokens: DataStoreTokens,
     val sharedPreferences: SharedPreferences
){
    suspend fun invoke(username: String, password: String): Flow<NetworkResult<AuthenticationResponse>> {

        val  result=  userRepository.doLogin(username, password)

        result.collect{result->
            if (result is NetworkResult.Success){

                val editor: SharedPreferences.Editor = sharedPreferences.edit()

                editor.putString("userEmail", username)
                editor.apply()
                result.data?.accessToken?.let { dataStoreTokens.saveAccessToken(it)
                    result.data?.refreshToken?.let { dataStoreTokens.saveRefreshToken(it) }

                }
            }
        }
        return result
    }
}