package com.farhanfarkaann.challenge5.viewmodeluser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhanfarkaann.challenge5.data.Repository
import com.farhanfarkaann.challenge5.data.room.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val  _dataUserRegist : MutableLiveData<Long> = MutableLiveData()
    val resultRegister : LiveData<Long> get() = _dataUserRegist

    private val  _dataUserLogin : MutableLiveData<User> = MutableLiveData()
    val resultLogin : LiveData<User> get() = _dataUserLogin

    private val _user : MutableLiveData<User> = MutableLiveData()
    val user : LiveData<User> get() = _user


    fun login(username:String, password:String){
        viewModelScope.launch {
            _dataUserLogin.value = repository.login(username, password)
        }
    }

    fun register(user: User){
        viewModelScope.launch {
            _dataUserRegist.value = repository.register(user)
        }
    }
    fun getDataUser(){
        viewModelScope.launch {
            repository.getUserPrefDataStore().collect {
                _user.value = it
            }
        }
    }
    fun setDataUser(user: User) {
        viewModelScope.launch {
            repository.saveToPrefDataStore(user)
        }
    }
}
