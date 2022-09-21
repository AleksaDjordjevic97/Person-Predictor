package com.aleksadjordjevic.personpredictor.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    suspend fun getNationalityPredictionsList(@Query("name")name:String):CountryList
}