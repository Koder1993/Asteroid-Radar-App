package com.udacity.asteroidradar.app

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.AsteroidRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RefreshAsteroidDataWorker @Inject constructor(
    @ApplicationContext context: Context,
    params: WorkerParameters,
    private val asteroidRepository: AsteroidRepository
) : CoroutineWorker(context, params) {

    companion object {
        const val WORKER_NAME = "RefreshAsteroidDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            asteroidRepository.refreshAsteroids()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}