package com.aleksadjordjevic.personpredictor.repositories

import com.aleksadjordjevic.personpredictor.db.CountryDao
import com.aleksadjordjevic.personpredictor.network.NationalityApiService
import javax.inject.Inject

class NationalityRepository @Inject constructor(
    private val apiService: NationalityApiService,
    private val countryDao: CountryDao
) {

    suspend fun getNationalityPredictions(name:String) = apiService.getNationalityPredictionsList(name)
    fun getCountryFromID(countryId:String) = countryDao.getCountryFromID(countryId)
}