package com.example.fitgo.data.retrofit.calls

import com.example.fitgo.domain.model.Ejercicios
import com.example.fitgo.domain.model.Entrenamientos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EjerciciosApi {
    @GET("/ejercicios")
    suspend fun getByEntrenamiento(@Query("idEntrenamiento")id: Int): Response<List<Ejercicios>>

}