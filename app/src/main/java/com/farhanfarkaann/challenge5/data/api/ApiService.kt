package com.farhanfarkaann.challenge5.data.api

import com.farhanfarkaann.challenge5.data.api.model.model_Popular.GetMoviesPopular
import com.farhanfarkaann.challenge5.data.api.model.model_TopRated.GetAllMovies
import com.farhanfarkaann.challenge5.data.api.model.model_UpComing.GetMoviesUpComing
import com.farhanfarkaann.challenge5.data.api.model.model_detail.DetailMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
     suspend fun getAllMovies(
        @Query("api_key") apiKey : String) :  Response <GetAllMovies>

    @GET("movie/popular")
    suspend  fun getAllMoviesPopular(
        @Query("api_key") apiKey : String): Response <GetMoviesPopular>

    @GET("movie/upcoming")
    suspend  fun getAllMoviesUpComing(
        @Query("api_key") apiKey : String): Response <GetMoviesUpComing>

    @GET("movie/{id}")
    suspend  fun getDetailMovie(
        @Path("id",) id: Int,
        @Query("api_key") apiKey : String): Response <DetailMoviesResponse>


}