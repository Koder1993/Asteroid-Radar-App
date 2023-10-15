package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

object AsteroidApi {

    private val moshiConverter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
        .baseUrl(NetworkConstants.BASE_URL)
        .build()

    private val asteroidRetrofitService: AsteroidRetrofitService by lazy {
        retrofit.create(AsteroidRetrofitService::class.java)
    }

    suspend fun getAsteroids(): List<Asteroid> {
        val asteroidsJsonResult =
            asteroidRetrofitService.getAllAsteroids("", "", NetworkConstants.API_KEY)
        return parseAsteroidsJsonResult(JSONObject(asteroidsJsonResult))
    }

    suspend fun getPictureOfDay() =
        asteroidRetrofitService.getPictureOfDay(NetworkConstants.API_KEY)
}