package com.aleksadjordjevic.personpredictor.network

import com.squareup.moshi.Json

data class Country(
    @Json(name = "country_id") val country_id:String,
    @Json(name = "probability") val probability:Float
)