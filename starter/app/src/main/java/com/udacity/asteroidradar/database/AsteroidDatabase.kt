package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao
}