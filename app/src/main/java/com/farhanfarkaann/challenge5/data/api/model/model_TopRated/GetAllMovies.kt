package com.farhanfarkaann.challenge5.data.api.model.model_TopRated


import com.google.gson.annotations.SerializedName

data class GetAllMovies(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)