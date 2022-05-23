package com.farhanfarkaann.challenge5.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreSetting(val dataStore: DataStore<Preferences>) {

    //Create some keys
    companion object {
        val REMEMBERED_KEY = booleanPreferencesKey("remembered_user")

    }

    //Store user data
    suspend fun storeUser( check: Boolean) {
        dataStore.edit {
            it[REMEMBERED_KEY] = check


        }
    }

    val userRememberedMe: Flow<Boolean?> = dataStore.data.map {
        it[REMEMBERED_KEY]
    }

    suspend fun clearSession () {
        dataStore.edit {
                it.clear()
        }
    }


}
