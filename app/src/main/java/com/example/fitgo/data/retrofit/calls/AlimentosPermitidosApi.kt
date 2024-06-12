package com.example.fitgo.data.retrofit.calls

import com.example.fitgo.domain.model.Alimentos
import com.example.fitgo.domain.model.AlimentosPermitidos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlimentosPermitidosApi {

    @GET("alimentosPermitidos/byDietaId")
    suspend fun getAllByDieta(@Query("iddieta")  iddieta:Int): Response<List<AlimentosPermitidos>>
}