package com.aleksadjordjevic.personpredictor.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aleksadjordjevic.personpredictor.R
import com.aleksadjordjevic.personpredictor.databinding.ActivityNameBinding

class NameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextListener()
        setOnClickListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun setTextListener() {
        binding.activityNameEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                if(s.isEmpty())
                    binding.activityNameEditTextArrowBtn.visibility = View.GONE
                else
                    binding.activityNameEditTextArrowBtn.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        })
    }

    private fun setOnClickListeners() {
        binding.activityNameEditTextArrowBtn.setOnClickListener { onActivityNameArrowBtnClick() }
    }

    private fun onActivityNameArrowBtnClick() {
        goToPredictionsActivity()
    }

    private fun goToPredictionsActivity() {
        val predictionsActivityIntent = Intent(this, PredictionsActivity::class.java)
        val userName = binding.activityNameEditText.text.trim().toString()
        predictionsActivityIntent.putExtra("USER_NAME", userName)
        startActivity(predictionsActivityIntent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


}