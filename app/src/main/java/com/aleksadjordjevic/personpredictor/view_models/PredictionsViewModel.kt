package com.aleksadjordjevic.personpredictor.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksadjordjevic.personpredictor.network.*
import com.aleksadjordjevic.personpredictor.repositories.GenderRepository
import com.aleksadjordjevic.personpredictor.repositories.NationalityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PredictionsViewModel @Inject constructor(
    val nationalityRepository: NationalityRepository,
    val genderRepository: GenderRepository
):ViewModel() {

   // private val nationalityRepository: NationalityRepository = NationalityRepository(NationalityApiClient.apiService),
   // private val genderRepository: GenderRepository = GenderRepository(GenderApiClient.apiService)

    private var _nationalitiesLiveData = MutableLiveData<ScreenState<List<Country>?>>()
    val nationalitiesLiveData:LiveData<ScreenState<List<Country>?>>
        get() = _nationalitiesLiveData

    private var _genderLiveData = MutableLiveData<ScreenState<Gender>?>()
    val genderLiveData:LiveData<ScreenState<Gender>?>
        get() = _genderLiveData


    fun fetchNationalities(name:String) {

        _nationalitiesLiveData.postValue(ScreenState.Loading(null))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = nationalityRepository.getNationalityPredictions(name)
                _nationalitiesLiveData.postValue(ScreenState.Success(client.countries))
            }catch (e:Exception){
                _nationalitiesLiveData.postValue(ScreenState.Error(e.message, null))
            }
        }
    }

    fun getCountryByID(countryID:String) = nationalityRepository.getCountryFromID(countryID)

    fun fetchGenderPrediction(name: String)
    {
        _genderLiveData.postValue(ScreenState.Loading(null))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = genderRepository.getGenderPredictions(name)
                _genderLiveData.postValue(ScreenState.Success(client))
            }catch (e:Exception){
                _genderLiveData.postValue(ScreenState.Error(e.message,null))
            }

        }
    }
}