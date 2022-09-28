package com.aleksadjordjevic.personpredictor.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aleksadjordjevic.personpredictor.R
import com.aleksadjordjevic.personpredictor.adapters.CountryListAdapter
import com.aleksadjordjevic.personpredictor.network.ScreenState
import com.aleksadjordjevic.personpredictor.databinding.ActivityPredictionsBinding
import com.aleksadjordjevic.personpredictor.network.Country
import com.aleksadjordjevic.personpredictor.network.Gender
import com.aleksadjordjevic.personpredictor.other.Constants
import com.aleksadjordjevic.personpredictor.view_models.PredictionsViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class PredictionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictionsBinding
    private val predictionsViewModel: PredictionsViewModel by lazy {
        ViewModelProvider(this).get(PredictionsViewModel::class.java)
    }

    @Inject
    lateinit var glide:RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
        setupUserName()
        setupPredictionsObserver()
        getAllPredictions()
    }

    private fun setOnClickListeners() {
        binding.btnBackPrediction.setOnClickListener { onBackBtnClick() }
    }

    private fun onBackBtnClick() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onBackBtnClick()
    }

    private fun setupUserName() {

        val userName = intent.getStringExtra("USER_NAME") ?: ""
        binding.txtUserName.text = userName
    }

    private fun setupPredictionsObserver() {

        predictionsViewModel.nationalitiesLiveData.observe(this){state->
            processNationalityResponse(state)
        }

        predictionsViewModel.genderLiveData.observe(this){state->
            processGenderResponse(state)
        }
    }

    private fun getAllPredictions() {

        val name = intent.getStringExtra("USER_NAME") ?: ""
        predictionsViewModel.fetchNationalities(name)
        predictionsViewModel.fetchGenderPrediction(name)
    }

    private fun processNationalityResponse(state: ScreenState<List<Country>?>?) {
        when(state){

            is ScreenState.Loading ->{
                Log.d(Constants.NATIONALITY_LOG_TAG,"Loading Nationality Response")
            }

            is ScreenState.Success ->{
                Log.d(Constants.NATIONALITY_LOG_TAG,"Successful Nationality Response")

                if(state.data != null) {

                    lifecycleScope.launch(Dispatchers.IO) {

                        val countryNameMap = createCountryNameMap(state.data)

                        runOnUiThread {
                            val countryListAdapter = CountryListAdapter(this@PredictionsActivity,state.data,countryNameMap)
                            binding.rcvNationalitiesList.adapter = countryListAdapter
                        }
                    }
                }
            }

            is ScreenState.Error ->{
                Log.d(Constants.NATIONALITY_LOG_TAG,"Error Retrieving Nationality Response")
            }

            else -> {}
        }
    }

    private fun createCountryNameMap(countryList:List<Country>):HashMap<String,String> {
        val countryNameMap = HashMap<String,String>()

        for(country in countryList) {
            val countryName = predictionsViewModel.getCountryByID(country.country_id)?.country_name
            countryName?.let {
                countryNameMap[country.country_id] = it
            }
        }

        return countryNameMap
    }


    private fun processGenderResponse(state: ScreenState<Gender>?) {
        when(state){

            is ScreenState.Loading ->{
                Log.d(Constants.GENDER_LOG_TAG,"Loading Gender Response")
            }

            is ScreenState.Success ->{

                Log.d(Constants.GENDER_LOG_TAG,"Successful Gender Response")

                if(state.data != null)
                    setGenderPercentages(state.data)
            }

            is ScreenState.Error ->{
                Log.d(Constants.GENDER_LOG_TAG,"Error Retrieving Gender Response")
            }

            else -> {}
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setGenderPercentages(gender: Gender) {

        val higherProbabilityGender = gender.gender

        val higherProbabilityGenderPercent = (gender.probability*100f).roundToInt()
        val lowerProbabilityGenderPercent = 100 - higherProbabilityGenderPercent

        if(higherProbabilityGender == "male") {
            binding.txtGenderMalePercent.text = "$higherProbabilityGenderPercent%"
            binding.txtGenderFemalePercent.text = "$lowerProbabilityGenderPercent%"
        } else {
            binding.txtGenderMalePercent.text = "$lowerProbabilityGenderPercent%"
            binding.txtGenderFemalePercent.text = "$higherProbabilityGenderPercent%"
        }
    }

}