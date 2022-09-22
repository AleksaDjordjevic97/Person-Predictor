package com.aleksadjordjevic.personpredictor.repositories

import com.aleksadjordjevic.personpredictor.network.NationalityApiService

class NationalityRepository(private val apiService: NationalityApiService) {

    suspend fun getNationalityPredictions(name:String) = apiService.getNationalityPredictionsList(name)
}