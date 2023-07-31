package com.udacity.asteroidradar.api

class NetworkDataSource {
    suspend fun getAsteroids() = AsteroidApi.getAsteroids()
    suspend fun getPictureOfDay() = AsteroidApi.getPictureOfDay()
}