package com.aleksadjordjevic.personpredictor.repositories

import com.aleksadjordjevic.personpredictor.network.GenderApiService
import javax.inject.Inject

class GenderRepository @Inject constructor(private val genderApiService: GenderApiService) {

    suspend fun getGenderPredictions(name:String) = genderApiService.getGenderPrediction(name)
}