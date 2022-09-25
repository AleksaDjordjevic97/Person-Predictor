package com.aleksadjordjevic.personpredictor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aleksadjordjevic.personpredictor.network.ScreenState
import com.aleksadjordjevic.personpredictor.databinding.ActivityPredictionsBinding
import com.aleksadjordjevic.personpredictor.network.Country
import com.aleksadjordjevic.personpredictor.view_models.PredictionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    private fun processGenderResponse(state: ScreenState<Float>?) {
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

    private fun getAllPredictions()
    {
        val name = intent.getStringExtra("USER_NAME") ?: ""
        predictionsViewModel.fetchNationalities(name)
        predictionsViewModel.fetchGenderPrediction(name)
    }


}