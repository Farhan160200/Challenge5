package com.farhanfarkaann.challenge5.viewmodeluser


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val pref: UserManager) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: "+modelClass.name)
    }
}