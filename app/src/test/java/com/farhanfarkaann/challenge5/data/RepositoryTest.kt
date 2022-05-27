package com.farhanfarkaann.challenge5.data

import com.farhanfarkaann.challenge5.data.api.ApiHelper
import com.farhanfarkaann.challenge5.data.api.ApiService
import com.farhanfarkaann.challenge5.data.api.model.model_Popular.GetMoviesPopular
import com.farhanfarkaann.challenge5.data.api.model.model_TopRated.GetAllMovies
import com.farhanfarkaann.challenge5.data.api.model.model_UpComing.GetMoviesUpComing
import com.farhanfarkaann.challenge5.data.api.model.model_detail.DetailMoviesResponse
import com.farhanfarkaann.challenge5.data.room.dao.UserDao
import com.farhanfarkaann.challenge5.data.datastore.UserManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepositoryTest {
    //Collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var userDao: UserDao
    private lateinit var userManager: UserManager

    //System Under Test (SUT)
    private lateinit var repository : Repository

    @Before
    fun setUp() {
        //Mockk -> membantu mocking
        apiService = mockk()
        userDao = mockk()
        userManager  = mockk()
        apiHelper  = ApiHelper(apiService)

        repository  = Repository(apiHelper,userDao,userManager)
    }

    @Test
    fun getTopRatedMovies() : Unit  = runBlocking{
        //GIVEN
        val responseMovies  = mockk<Response<GetAllMovies>>()

        every {
            runBlocking {
                apiService.getAllMovies("a6e717a2fd3abac91324810090ae62ff")
            }
        } returns responseMovies

        //WHEN
        repository.getMoviesTopRated("a6e717a2fd3abac91324810090ae62ff")


        //THEN
        verify {
            runBlocking {
                apiService.getAllMovies("a6e717a2fd3abac91324810090ae62ff")
            }
        }
    }
    @Test
    fun getPopularMovies() : Unit  = runBlocking{
        //GIVEN
        val responseMovies  = mockk<Response<GetMoviesPopular>>()

        every {
            runBlocking {
                apiService.getAllMoviesPopular("a6e717a2fd3abac91324810090ae62ff")
            }
        } returns responseMovies

        //WHEN
        repository.getMoviesPopular("a6e717a2fd3abac91324810090ae62ff")


        //THEN
        verify {
            runBlocking {
                apiService.getAllMoviesPopular("a6e717a2fd3abac91324810090ae62ff")
            }
        }
    }
    @Test
    fun getUpComingMovies() : Unit  = runBlocking{
        //GIVEN
        val responseMovies  = mockk<Response<GetMoviesUpComing>>()

        every {
            runBlocking {
                apiService.getAllMoviesUpComing("a6e717a2fd3abac91324810090ae62ff")
            }
        } returns responseMovies

        //WHEN
        repository.getMoviesUpComing("a6e717a2fd3abac91324810090ae62ff")


        //THEN
        verify {
            runBlocking {
                apiService.getAllMoviesUpComing("a6e717a2fd3abac91324810090ae62ff")
            }
        }
    }
    @Test
    fun getDetailMovies() : Unit  = runBlocking{
        //GIVEN
        val responseMovies  = mockk<Response<DetailMoviesResponse>>()

        every {
            runBlocking {
                apiService.getDetailMovie(278,"a6e717a2fd3abac91324810090ae62ff")
            }
        } returns responseMovies

        //WHEN
        repository.getDetailMovies(278,"a6e717a2fd3abac91324810090ae62ff")


        //THEN
        verify {
            runBlocking {
                apiService.getDetailMovie(278,"a6e717a2fd3abac91324810090ae62ff")
            }
        }
    }
}