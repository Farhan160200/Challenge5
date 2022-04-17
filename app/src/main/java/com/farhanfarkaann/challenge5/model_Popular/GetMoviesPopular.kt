package com.farhanfarkaann.challenge5.model_Popular


import com.google.gson.annotations.SerializedName

data class GetMoviesPopular(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultPopular>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)