package com.aleksadjordjevic.personpredictor.network

import com.squareup.moshi.Json

data class CountryList(
    @Json(name = "country") val countries: List<Country>
)