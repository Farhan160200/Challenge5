package com.farhanfarkaann.challenge5.model_UpComing


import com.google.gson.annotations.SerializedName

data class GetMoviesUpComing(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultUpComing>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)