package com.aleksadjordjevic.personpredictor.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aleksadjordjevic.personpredictor.db.entities.Country


@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {

    abstract fun getCountryDao():CountryDao
}