package com.udacity.asteroidradar.di

import android.app.Application
import androidx.room.Room
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.NetworkDataSource
import com.udacity.asteroidradar.database.AsteroidDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {
    @Provides
    @Singleton
    fun provideDb(application: Application): AsteroidDatabase {
        return Room.databaseBuilder(
            application,
            AsteroidDatabase::class.java,
            "AsteroidDatabase"
        ).build()
    }

    @Provides
    fun provideNetworkDataSource() = NetworkDataSource()

    @Provides
    fun provideDispatcher() = Dispatchers.IO
}