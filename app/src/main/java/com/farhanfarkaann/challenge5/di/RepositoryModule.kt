package com.farhanfarkaann.challenge5.di

import com.farhanfarkaann.challenge5.data.Repository
import com.farhanfarkaann.challenge5.data.api.ApiHelper
import com.farhanfarkaann.challenge5.data.datastore.DataStoreSetting
import com.farhanfarkaann.challenge5.room.dao.UserDao
import com.farhanfarkaann.challenge5.viewmodeluser.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideRepository(
        apiHelper: ApiHelper,
        userDao: UserDao,
        userManager: UserManager

    ) = Repository(apiHelper,userDao,userManager)
}