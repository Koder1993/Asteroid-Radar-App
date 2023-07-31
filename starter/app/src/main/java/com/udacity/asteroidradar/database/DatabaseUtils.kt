package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Asteroid

object DatabaseUtils {

    const val ASTEROID_TABLE_NAME = "asteroid"

    fun List<AsteroidEntity>.toAsteroidList(): List<Asteroid> = map {
        Asteroid(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous
        )
    }
}