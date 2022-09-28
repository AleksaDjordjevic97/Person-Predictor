package com.aleksadjordjevic.personpredictor.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.aleksadjordjevic.personpredictor.R
import com.aleksadjordjevic.personpredictor.network.Country
import com.aleksadjordjevic.personpredictor.other.Constants
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class CountryListAdapter(
    var context: Context,
    var countryList: List<Country>,
    var countryNameMap:HashMap<String,String>): RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {

    inner class CountryListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val layoutCountryListItem = itemView.findViewById<ConstraintLayout>(R.id.countryListItem)
        val txtCountryName = itemView.findViewById<TextView>(R.id.countryName)
        val txtCountryPercent = itemView.findViewById<TextView>(R.id.countryPercent)
        val imgCountryFlag = itemView.findViewById<ImageView>(R.id.countryFlag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_nationality, parent, false)
        return CountryListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {

        val currCountry = countryList[position]

        if(countryNameMap[currCountry.country_id] != null){
            holder.txtCountryName.text = countryNameMap[currCountry.country_id]
            holder.txtCountryPercent.text = "${(currCountry.probability*100f).roundToInt()}%"

            val flagUrl = "${Constants.COUNTRY_FLAG_API_BASE_URL}${currCountry.country_id.lowercase()}.png"
            Glide.with(context).load(flagUrl).into(holder.imgCountryFlag)
        }else
            holder.layoutCountryListItem.layoutParams = ConstraintLayout.LayoutParams(0,0)

    }

}