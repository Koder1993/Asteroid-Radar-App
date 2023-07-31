package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidRetrofitService {

    @GET(NetworkConstants.HTTP_GET_FEED_PATH)
    suspend fun getAllAsteroids(
        @Query(NetworkConstants.QUERY_START_DATE_ARG) startDate: String,
        @Query(NetworkConstants.QUERY_END_DATE_ARG) endDate: String,
        @Query(NetworkConstants.QUERY_API_KEY_ARG) apiKey: String
    ): String // JSON string containing all asteroids list as output

    @GET(NetworkConstants.HTTP_GET_PIC_OF_DAY_PATH)
    suspend fun getPictureOfDay(@Query(NetworkConstants.QUERY_API_KEY_ARG) apiKey: String): PictureOfDay
}