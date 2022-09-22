package com.aleksadjordjevic.personpredictor.repositories

import com.aleksadjordjevic.personpredictor.network.GenderApiService

class GenderRepository(private val genderApiService: GenderApiService) {

    suspend fun getGenderPredictions(name:String) = genderApiService.getGenderPrediction(name)
}