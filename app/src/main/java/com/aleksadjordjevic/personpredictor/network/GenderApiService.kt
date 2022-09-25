package com.aleksadjordjevic.personpredictor.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GenderApiService {

    @GET(".")
    suspend fun getGenderPrediction(@Query("name") name: String):Gender
}