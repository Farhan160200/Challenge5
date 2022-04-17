package com.farhanfarkaann.challenge5.service

import com.farhanfarkaann.challenge5.model_Popular.GetMoviesPopular
import com.farhanfarkaann.challenge5.model_TopRated.GetAllMovies
import com.farhanfarkaann.challenge5.model_UpComing.GetMoviesUpComing
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie/now_playing")
    fun getAllMovies(): Call<GetAllMovies>

    @GET("movie/popular")
    fun getMoviesPopular(): Call<GetMoviesPopular>

    @GET("movie/upcoming")
    fun getMoviesUpComing(): Call<GetMoviesUpComing>


}