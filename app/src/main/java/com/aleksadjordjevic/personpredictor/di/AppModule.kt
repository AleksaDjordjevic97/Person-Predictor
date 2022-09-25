package com.aleksadjordjevic.personpredictor.di

import android.content.Context
import androidx.room.Room
import com.aleksadjordjevic.personpredictor.db.CountryDao
import com.aleksadjordjevic.personpredictor.db.CountryDatabase
import com.aleksadjordjevic.personpredictor.network.GenderApiClient
import com.aleksadjordjevic.personpredictor.network.GenderApiService
import com.aleksadjordjevic.personpredictor.network.NationalityApiClient
import com.aleksadjordjevic.personpredictor.network.NationalityApiService
import com.aleksadjordjevic.personpredictor.repositories.GenderRepository
import com.aleksadjordjevic.personpredictor.repositories.NationalityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCountryDatabase(
        @ApplicationContext context: Context,
        ) = Room.databaseBuilder(context, CountryDatabase::class.java, "countries.db")
        .createFromAsset("db/countries.db")
        .build()

    @Singleton
    @Provides
    fun provideCountryDao(
        db:CountryDatabase
    ) = db.getCountryDao()

    @Singleton
    @Provides
    fun provideNationalityApiService() = NationalityApiClient.apiService

    @Singleton
    @Provides
    fun provideNationalityRepository(
        apiService: NationalityApiService,
        countryDao: CountryDao
    ) = NationalityRepository(apiService,countryDao)

    @Singleton
    @Provides
    fun provideGenderApiService() = GenderApiClient.apiService

    @Singleton
    @Provides
    fun provideGenderRepository(
        apiService: GenderApiService
    ) = GenderRepository(apiService)
}