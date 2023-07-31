package com.udacity.asteroidradar.app

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class AsteroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        launchRecurringWork()
    }

    private fun launchRecurringWork() {
        CoroutineScope(Dispatchers.Default).launch {
            startWorkToRefreshAsteroidData()
        }
    }

    private fun startWorkToRefreshAsteroidData() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .apply {
                setRequiresDeviceIdle(true)
            }
            .build()

        val request = PeriodicWorkRequestBuilder<RefreshAsteroidDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                RefreshAsteroidDataWorker.WORKER_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }
}