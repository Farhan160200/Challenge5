package com.farhanfarkaann.challenge5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhanfarkaann.challenge5.model_Popular.GetMoviesPopular
import com.farhanfarkaann.challenge5.model_TopRated.GetAllMovies
import com.farhanfarkaann.challenge5.model_UpComing.GetMoviesUpComing
import com.farhanfarkaann.challenge5.model_detail.DetailMoviesResponse
import com.farhanfarkaann.challenge5.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel()  {
    val error: MutableLiveData<String> = MutableLiveData()

    val isLoading = MutableLiveData<Boolean>()
    private val _dataMovieTopRated: MutableLiveData<GetAllMovies> by lazy {
        MutableLiveData<GetAllMovies>().also {
            getAllMoviesTopRated()
        }
    }
    private val _dataMoviePoPular : MutableLiveData<GetMoviesPopular> by lazy {
        MutableLiveData<GetMoviesPopular>().also {
            getAllMoviesPopular()
        }
    }
    private val _dataMovieUpComing : MutableLiveData<GetMoviesUpComing> by lazy {
        MutableLiveData<GetMoviesUpComing>().also {
            getAllMoviesUpcoming()
        }
    }
    val isLoadingDetail = MutableLiveData<Boolean>()
    private val _detailMovieTopRated: MutableLiveData<DetailMoviesResponse> = MutableLiveData()
    val errorDetail: MutableLiveData<String> = MutableLiveData()
    val detailMovieTopRated: LiveData<DetailMoviesResponse> = _detailMovieTopRated




    val dataMovies : LiveData<GetAllMovies> = _dataMovieTopRated
    val dataMoviesPopular : LiveData<GetMoviesPopular> = _dataMoviePoPular
    val dataMoviesUpcoming : LiveData<GetMoviesUpComing> = _dataMovieUpComing



    private fun getAllMoviesUpcoming() {
        isLoading.postValue(true )
        ApiClient.instance.getMoviesUpComing().enqueue(object : Callback<GetMoviesUpComing>{
            override fun onResponse(
                call: Call<GetMoviesUpComing>,
                response: Response<GetMoviesUpComing>
            ) {
                isLoading.postValue(false )
                val body = response.body()

                if (response.code()==200)   {
                    _dataMovieUpComing.postValue(body)
                } else {
                    error.postValue("Error")
                }

            }

            override fun onFailure(call: Call<GetMoviesUpComing>, t: Throwable) {
                isLoading.postValue(false)
            }

        })

    }





    private fun getAllMoviesPopular() {
        isLoading.postValue(true )
        ApiClient.instance.getMoviesPopular().enqueue(object : Callback<GetMoviesPopular>{
            override fun onResponse(
                call: Call<GetMoviesPopular>,
                response: Response<GetMoviesPopular>
            ) {
                isLoading.postValue(false )
                val body = response.body()
                if (response.code()==200)   {
                    _dataMoviePoPular.postValue(body)
                } else {
                    error.postValue("Error")
                }
            }

            override fun onFailure(call: Call<GetMoviesPopular>, t: Throwable) {
                isLoading.postValue(false)
            }


        })
    }



    fun getAllMoviesTopRated(){
        isLoading.postValue(true )
        ApiClient.instance.getAllMovies().enqueue(object : Callback<GetAllMovies>{
            override fun onResponse(
                call: Call<GetAllMovies>,
                response: Response<GetAllMovies>
            ) {
                isLoading.postValue(false )
                val body = response.body()
                if (response.code()==200)   {
                    _dataMovieTopRated.postValue(body)
                }else{
                    error.postValue("Error")
                }
            }

            override fun onFailure(call: Call<GetAllMovies>, t: Throwable) {
               isLoading.postValue(false)
            }

        }
        )
    }

    fun getDetailMovies(id: Int){
        isLoadingDetail.postValue(true)
        ApiClient.instance.getDetailMovie(id).enqueue(object : Callback<DetailMoviesResponse> {
            override fun onResponse(call: Call<DetailMoviesResponse>, response: Response<DetailMoviesResponse>) {
                isLoading.postValue(false)
                if (response.code() == 200){
                    _detailMovieTopRated.postValue(response.body())
                }else{
                    errorDetail.postValue("Error")
                }
            }

            override fun onFailure(call: Call<DetailMoviesResponse>, t: Throwable) {
                isLoadingDetail.postValue(false)
            }
        })
    }



}


