package com.farhanfarkaann.challenge5.data.api


class ApiHelper(private val apiService: ApiService) {

    suspend fun getAllMovies(apiKey: String) = apiService.getAllMovies(apiKey)
    suspend fun getAllMoviesPopular(apiKey: String) = apiService.getAllMoviesPopular(apiKey)
    suspend fun getAllMoviesUpComing(apiKey: String) = apiService.getAllMoviesUpComing(apiKey)
    suspend fun getDetailMovies(id : Int, apiKey: String) = apiService.getDetailMovie(id,apiKey)

}