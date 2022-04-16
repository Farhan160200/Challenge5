package com.farhanfarkaann.challenge5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhanfarkaann.challenge5.model.GetAllMovies
import com.farhanfarkaann.challenge5.model.Result
import com.farhanfarkaann.challenge5.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel()  {
    val error: MutableLiveData<String> = MutableLiveData()

    val isLoading = MutableLiveData<Boolean>()
    private val _dataMovie: MutableLiveData<GetAllMovies> by lazy {
        MutableLiveData<GetAllMovies>().also {
            getAllMovies()
        }
    }
    val dataMovies : LiveData<GetAllMovies> = _dataMovie

    fun getAllMovies(){
        isLoading.postValue(true )
        ApiClient.instance.getAllMovies().enqueue(object : Callback<GetAllMovies>{
            override fun onResponse(call: Call<GetAllMovies>, response: Response<GetAllMovies>) {
                                isLoading.postValue(false )
                val body = response.body()
                if (response.code()==200)   {
                    _dataMovie.postValue(body)
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

}


