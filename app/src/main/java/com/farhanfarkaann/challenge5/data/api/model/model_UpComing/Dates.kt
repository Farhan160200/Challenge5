package com.farhanfarkaann.challenge5.data.api.model.model_UpComing


import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("maximum")
    val maximum: String,
    @SerializedName("minimum")
    val minimum: String
)