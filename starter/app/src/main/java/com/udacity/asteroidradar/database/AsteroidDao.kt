package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM ${DatabaseUtils.ASTEROID_TABLE_NAME} ORDER by closeApproachDate ASC")
    fun getAllAsteroids(): Flow<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroids(asteroids: List<AsteroidEntity>)

    @Query("SELECT * FROM ${DatabaseUtils.ASTEROID_TABLE_NAME} WHERE closeApproachDate == :currentDay ORDER by closeApproachDate ASC")
    fun getAsteroidsForDay(currentDay: String): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM ${DatabaseUtils.ASTEROID_TABLE_NAME} WHERE closeApproachDate BETWEEN :startDate and :endDate ORDER by closeApproachDate ASC")
    fun getAsteroidsForWeek(startDate: String, endDate: String): Flow<List<AsteroidEntity>>
}