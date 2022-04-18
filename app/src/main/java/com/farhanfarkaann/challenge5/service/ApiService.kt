package com.farhanfarkaann.challenge5.service

import com.farhanfarkaann.challenge5.model_Popular.GetMoviesPopular
import com.farhanfarkaann.challenge5.model_TopRated.GetAllMovies
import com.farhanfarkaann.challenge5.model_UpComing.GetMoviesUpComing
import com.farhanfarkaann.challenge5.model_detail.DetailMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/top_rated")
    fun getAllMovies(): Call<GetAllMovies>

    @GET("movie/popular")
    fun getMoviesPopular(): Call<GetMoviesPopular>

    @GET("movie/upcoming")
    fun getMoviesUpComing(): Call<GetMoviesUpComing>

    @GET("movie/{id}")
    fun getDetailMovie(@Path("id") id: Int): Call<DetailMoviesResponse>




}