package com.farhanfarkaann.challenge5.viewmodeluser



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanfarkaann.challenge5.data.Repository
import com.farhanfarkaann.challenge5.data.api.Resource
import com.farhanfarkaann.challenge5.data.api.model.model_Popular.GetMoviesPopular
import com.farhanfarkaann.challenge5.data.api.model.model_TopRated.GetAllMovies
import com.farhanfarkaann.challenge5.data.api.model.model_UpComing.GetMoviesUpComing
import com.farhanfarkaann.challenge5.data.api.model.model_detail.DetailMoviesResponse


import com.farhanfarkaann.challenge5.data.room.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    val isLoading = MutableLiveData<Boolean>()
    private val _dataMovieTopRated : MutableLiveData<Resource<Response<GetAllMovies>>> = MutableLiveData()
    val dataMovieTopRated : LiveData<Resource<Response<GetAllMovies>>> get() = _dataMovieTopRated

    private val _dataMoviePoPularrr :  MutableLiveData<Resource<Response<GetMoviesPopular>>> = MutableLiveData()
    val dataMoviePopular : LiveData<Resource<Response<GetMoviesPopular>>> get() = _dataMoviePoPularrr

    private val _dataMovieUpCominggg : MutableLiveData<Resource<Response<GetMoviesUpComing>>> = MutableLiveData()
    val dataMovieUpComing  : LiveData<Resource<Response<GetMoviesUpComing>>> get() = _dataMovieUpCominggg

    private val _detailMovie : MutableLiveData<Resource<Response<DetailMoviesResponse>>> = MutableLiveData()
            val detailMovie : LiveData<Resource<Response<DetailMoviesResponse>>> get() = _detailMovie

    private val _uiState: MutableStateFlow<DetailMovieUiState> =
        MutableStateFlow(DetailMovieUiState())
    val uiState: StateFlow<DetailMovieUiState> get() = _uiState.asStateFlow()



    fun getMoviesUpcoming(apiKey : String) {
        viewModelScope.launch {
            _dataMovieUpCominggg.postValue(Resource.loading())
            try {
                _dataMovieUpCominggg.postValue(Resource.success(repository.getMoviesUpComing(apiKey)))
            } catch (exception: Exception) {
                _dataMovieUpCominggg.postValue(
                    Resource.error(
                        exception.localizedMessage ?: "Error occured"))
            }
        }
    }


    fun getMoviesPopular(apiKey : String) {
        viewModelScope.launch {
            _dataMoviePoPularrr.postValue(Resource.loading())
            try {
                _dataMoviePoPularrr.postValue(Resource.success(repository.getMoviesPopular(apiKey)))
            } catch (exception: Exception) {
                _dataMoviePoPularrr.postValue(
                    Resource.error(
                        exception.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }


    fun getMoviesTopRated(apiKey : String) {
        viewModelScope.launch {
            _dataMovieTopRated.postValue(Resource.loading())
            try {
                _dataMovieTopRated.postValue(Resource.success(repository.getMoviesTopRated(apiKey)))
            } catch (exception: Exception) {
                _dataMovieTopRated.postValue(
                    Resource.error(
                        exception.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }


    fun getDetailMovies(id: Int, apiKey: String) {
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getDetailMovies(id, apiKey)))
            } catch (exception: Exception) {
                _detailMovie.postValue(
                    Resource.error(
                        exception.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }


    fun getDataUser() {
        viewModelScope.launch {
            repository.getUserPrefDataStore().collect {
                _user.value = it
            }
        }
    }


    fun deleteUserPref() {
        viewModelScope.launch {
            repository.deletePrefDataStore()
        }
    }
}

data class DetailMovieUiState(
    val isLoading: Boolean = false,
    val movie: DetailMoviesResponse? = null,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
)


