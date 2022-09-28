package com.aleksadjordjevic.personpredictor.db

import androidx.room.Dao
import androidx.room.Query
import com.aleksadjordjevic.personpredictor.db.entities.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries WHERE country_id LIKE :countryId LIMIT 1")
    fun getCountryFromID(countryId:String): Country?
}