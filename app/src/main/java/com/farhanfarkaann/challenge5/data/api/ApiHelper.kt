package com.farhanfarkaann.challenge5.data.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHelper(private val apiService: ApiService) {
    suspend fun getAllMovies(apiKey: String) = apiService.getAllMovies(apiKey)
    suspend fun getAllMoviesPopular(apiKey: String) = apiService.getAllMoviesPopular(apiKey)
    suspend fun getAllMoviesUpComing(apiKey: String) = apiService.getAllMoviesUpComing(apiKey)
    suspend fun getDetailMovies(id : Int, apiKey: String) = apiService.getDetailMovie(id,apiKey)

}