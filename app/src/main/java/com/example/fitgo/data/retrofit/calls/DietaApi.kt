package com.example.fitgo.data.retrofit.calls

import com.example.fitgo.domain.model.Dieta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DietaApi {

    @GET("dietas/byId")
    suspend fun getDieta(@Query("id")id:Int):Response<Dieta>
    @GET("dietas/byEmail")
    suspend fun getAllDietaByEmail(@Query("email")email:String):Response<List<Dieta>>
}