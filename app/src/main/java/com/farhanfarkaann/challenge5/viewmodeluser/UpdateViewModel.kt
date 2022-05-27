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
class UpdateViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _user : MutableLiveData<User> = MutableLiveData()
    val user : LiveData<User> get() = _user

    private val _resultUpdate : MutableLiveData<Int> = MutableLiveData()
    val resultUpdate : LiveData<Int> get() = _resultUpdate

    fun setDataUser(user: User) {
        viewModelScope.launch {
            repository.saveToPrefDataStore(user)
        }
    }

    fun getDataUser(){
        viewModelScope.launch {
            repository.getUserPrefDataStore().collect {
                _user.value = it
            }
        }
    }
    fun update(user:User){
        viewModelScope.launch {
            _resultUpdate.value = repository.update(user)
        }
    }
}