package com.farhanfarkaann.challenge5.viewmodeluser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.farhanfarkaann.challenge5.room.entity.User

class HomeViewModel(private val pref: UserManager) : ViewModel() {

    suspend fun setDataUser(user: User) {
        pref.setUser(user)
    }

    fun getDataUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}