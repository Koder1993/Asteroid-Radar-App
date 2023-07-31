package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.NetworkDataSource
import com.udacity.asteroidradar.api.asAsteroidEntityList
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.DatabaseUtils.toAsteroidList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AsteroidRepository @Inject constructor(
    asteroidDatabase: AsteroidDatabase,
    private val networkDataSource: NetworkDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val dao = asteroidDatabase.asteroidDao()

    fun getAsteroids(): Flow<List<Asteroid>> {
        return dao.getAllAsteroids().map {
            it.toAsteroidList()
        }
    }

    fun getAsteroidsForDay(currentDay: String): Flow<List<Asteroid>> {
        return dao.getAsteroidsForDay(currentDay).map {
            it.toAsteroidList()
        }
    }

    fun getAsteroidsForWeek(startDate: String, endDate: String): Flow<List<Asteroid>> {
        return dao.getAsteroidsForWeek(startDate, endDate).map {
            it.toAsteroidList()
        }
    }

    // function is main-safe since it takes care of transferring the operation to IO thread
    suspend fun refreshAsteroids() {
        withContext(defaultDispatcher) {
            val asteroids = networkDataSource.getAsteroids()
            dao.insertAsteroids(asteroids.asAsteroidEntityList())
        }
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        return networkDataSource.getPictureOfDay()
    }
}