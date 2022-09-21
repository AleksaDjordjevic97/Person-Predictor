package com.aleksadjordjevic.personpredictor

import com.aleksadjordjevic.personpredictor.network.ApiService
import com.aleksadjordjevic.personpredictor.network.CountryList

class Repository(private val apiService: ApiService) {

    suspend fun getNationalityPredictions(name:String) = apiService.getNationalityPredictionsList(name)
}