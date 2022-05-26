package com.farhanfarkaann.challenge5.data.room.dao

import androidx.room.*
import com.farhanfarkaann.challenge5.data.room.entity.User


@Dao
interface UserDao {


    @Query("SELECT * FROM User WHERE username like :username and password like :password")
   suspend fun getUser(username: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun addUser(user: User): Long

    @Update
    suspend fun updateItem(user: User):Int
}

