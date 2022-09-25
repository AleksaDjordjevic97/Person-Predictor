package com.aleksadjordjevic.personpredictor.network

import com.squareup.moshi.Json

data class Gender(
    @Json(name = "gender") val gender: String,
    @Json(name = "probability") val probability:Float
)
