package com.example.fitgo.data.retrofit.calls

import com.example.fitgo.domain.model.Coachee
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoacheeApi {
    @GET("/coachee/getByEmail")
    suspend fun getCoacheeByEmail(@Query("email")email:String):Response<Coachee>
}