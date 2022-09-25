package com.aleksadjordjevic.personpredictor.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.aleksadjordjevic.personpredictor.network.ScreenState
import com.aleksadjordjevic.personpredictor.databinding.ActivityPredictionsBinding
import com.aleksadjordjevic.personpredictor.network.Country
import com.aleksadjordjevic.personpredictor.network.Gender
import com.aleksadjordjevic.personpredictor.other.Constants
import com.aleksadjordjevic.personpredictor.view_models.PredictionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class PredictionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictionsBinding
    private val predictionsViewModel: PredictionsViewModel by lazy {
        ViewModelProvider(this).get(PredictionsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUserName()
        setupPredictionsObserver()
        getAllPredictions()
    }

    private fun setupUserName() {

        val userName = intent.getStringExtra("USER_NAME") ?: ""
        binding.txtUserName.text = userName
    }

    private fun setupPredictionsObserver()
    {
        predictionsViewModel.nationalitiesLiveData.observe(this){state->
            processNationalityResponse(state)
        }

        predictionsViewModel.genderLiveData.observe(this){state->
            processGenderResponse(state)
        }
    }

    private fun processNationalityResponse(state: ScreenState<List<Country>?>?) {
        when(state){
            is ScreenState.Loading ->{

            }
            is ScreenState.Success ->{
                if(state.data != null)
                {

                }
            }
            is ScreenState.Error ->{

            }
            else -> {}
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processGenderResponse(state: ScreenState<Gender>?) {
        when(state){
            is ScreenState.Loading ->{
                Log.d(Constants.GENDER_LOG_TAG,"Loading Gender Response")
            }

            is ScreenState.Success ->{

                Log.d(Constants.GENDER_LOG_TAG,"Successful Gender Response")

                if(state.data != null)
                {
                    val higherProbabilityGender = state.data.gender

                    val higherProbabilityGenderPercent = (state.data.probability*100f).roundToInt()
                    val lowerProbabilityGenderPercent = 100 - higherProbabilityGenderPercent

                    if(higherProbabilityGender == "male")
                    {
                        binding.txtGenderMalePercent.text = "$higherProbabilityGenderPercent%"
                        binding.txtGenderFemalePercent.text = "$lowerProbabilityGenderPercent%"
                    }
                    else
                    {
                        binding.txtGenderMalePercent.text = "$lowerProbabilityGenderPercent%"
                        binding.txtGenderFemalePercent.text = "$higherProbabilityGenderPercent%"
                    }
                }
            }

            is ScreenState.Error ->{
                Log.d(Constants.GENDER_LOG_TAG,"Error Retrieving Gender Response")
            }
            else -> {}
        }
    }

    private fun getAllPredictions()
    {
        val name = intent.getStringExtra("USER_NAME") ?: ""
        predictionsViewModel.fetchNationalities(name)
        predictionsViewModel.fetchGenderPrediction(name)
    }


}