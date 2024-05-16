package com.example.fitgo.data.retrofit.calls

import com.example.fitgo.data.model.AuthenticationResponse
import com.example.fitgo.domain.model.Entrenamientos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EntrenamientosApi {

    @GET("/entrenamientos/getByEmail")
    suspend fun getAllEntrenamientos(@Query("email") email: String): Response<List<Entrenamientos>>



}