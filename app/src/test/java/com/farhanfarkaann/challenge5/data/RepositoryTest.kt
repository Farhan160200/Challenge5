package com.farhanfarkaann.challenge5.data

import com.farhanfarkaann.challenge5.data.api.ApiHelper
import com.farhanfarkaann.challenge5.data.api.ApiService
import com.farhanfarkaann.challenge5.data.api.model.model_TopRated.GetAllMovies
import com.farhanfarkaann.challenge5.room.dao.UserDao
import com.farhanfarkaann.challenge5.viewmodeluser.UserManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.http.Query

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
    fun getPopularMovies() : Unit  = runBlocking{
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
}