package com.farhanfarkaann.challenge5.service

import com.farhanfarkaann.challenge5.model.GetAllMovies
import com.farhanfarkaann.challenge5.model.Result
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie/now_playing")
    fun getAllMovies(): Call<GetAllMovies>
}