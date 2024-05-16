package com.example.fitgo.data.retrofit.calls

import com.example.fitgo.data.model.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("/login")
    suspend fun login(@Query("email") email: String, @Query("password") password: String): Response<AuthenticationResponse>

    @GET("/register")
    suspend fun register(@Query("email")email:String,@Query("password") password:String,@Query("username") username:String):Response<Boolean>
    @GET("/refreshtoken")
    suspend fun getAccessToken(@Query("refreshtoken")refreshtoken:String):Response<String>

}

