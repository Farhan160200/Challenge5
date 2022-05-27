package com.farhanfarkaann.challenge5.data

import com.farhanfarkaann.challenge5.data.api.ApiHelper
import com.farhanfarkaann.challenge5.data.room.dao.UserDao
import com.farhanfarkaann.challenge5.data.room.entity.User
import com.farhanfarkaann.challenge5.data.datastore.UserManager
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao,
    private val userManager: UserManager
) {
    //api
    suspend fun getMoviesTopRated(apiKey : String ) = apiHelper.getAllMovies(apiKey)
    suspend fun getMoviesPopular(apiKey : String) = apiHelper.getAllMoviesPopular(apiKey)
    suspend fun getMoviesUpComing(apiKey: String ) = apiHelper.getAllMoviesUpComing(apiKey)
    suspend fun getDetailMovies(id: Int, apiKey: String) = apiHelper.getDetailMovies(id,apiKey)
    //DataStore
    suspend fun saveToPrefDataStore(user: User) {
        userManager.setUserDataStore(user)
    }
    fun getUserPrefDataStore(): Flow<User> {
        return userManager.getUserDataStore()
    }
    suspend fun deletePrefDataStore() {
        userManager.deleteUserFromPrefDataStore()
    }
    //room
    suspend fun register(user: User): Long = userDao.addUser(user)
    suspend fun login(username: String, password: String ): User {
        return userDao.getUser(username, password)
    }
    suspend fun update(user: User): Int = userDao.updateItem(user)
}