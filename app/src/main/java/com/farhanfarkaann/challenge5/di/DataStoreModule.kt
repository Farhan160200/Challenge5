package com.farhanfarkaann.challenge5.di

import android.content.Context
import com.farhanfarkaann.challenge5.data.datastore.DataStoreSetting
import com.farhanfarkaann.challenge5.viewmodeluser.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context) = UserManager(context)
}