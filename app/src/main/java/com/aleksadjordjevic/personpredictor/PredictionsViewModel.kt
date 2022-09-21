package com.aleksadjordjevic.personpredictor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksadjordjevic.personpredictor.network.ApiClient
import com.aleksadjordjevic.personpredictor.network.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PredictionsViewModel(
    private val repository: Repository = Repository(ApiClient.apiService)
):ViewModel() {

    private var _nationalitiesLiveData = MutableLiveData<ScreenState<List<Country>?>>()
    val nationalitiesLiveData:LiveData<ScreenState<List<Country>?>>
        get() = _nationalitiesLiveData


    fun fetchNationalities(name:String) {

        _nationalitiesLiveData.postValue(ScreenState.Loading(null))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = repository.getNationalityPredictions(name)
                _nationalitiesLiveData.postValue(ScreenState.Success(client.countries))
            }catch (e:Exception){
                _nationalitiesLiveData.postValue(ScreenState.Error(e.message,null))
            }
        }

    }
}