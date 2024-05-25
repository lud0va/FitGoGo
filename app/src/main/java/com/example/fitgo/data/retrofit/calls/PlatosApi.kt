package com.example.fitgo.data.retrofit.calls

import com.example.fitgo.domain.model.Plato
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlatosApi {

    @GET("/plato/getByDieta")
    suspend fun getAllByDieta(@Query("iddieta") iddieta:Int): Response<List<Plato>>
}