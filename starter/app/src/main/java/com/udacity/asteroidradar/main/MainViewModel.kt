package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val asteroidRepository: AsteroidRepository
) : ViewModel() {

    // state flow for asteroid list
    private var _asteroidUiState = MutableStateFlow<List<Asteroid>>(mutableListOf())
    val asteroidUiState = _asteroidUiState.asStateFlow()

    // state flow for pictureOfDay
    private var _pictureOfDayUiState = MutableStateFlow(PictureOfDayData(false, null))
    val pictureOfDayUiState = _pictureOfDayUiState.asStateFlow()

    private var dataFlow = asteroidRepository.getAsteroids()

    private var job: Job? = null

    init {
        refreshAsteroidsData()
        getPictureOfDay()
        observeAsteroidList()
    }

    private fun observeAsteroidList() {
        job?.cancel()
        job = viewModelScope.launch {
           dataFlow.collect {
                val asteroidList = mutableListOf<Asteroid>()
                asteroidList.addAll(it)
                _asteroidUiState.value = asteroidList
            }
        }
    }

    private fun refreshAsteroidsData() {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            _pictureOfDayUiState.value =
                PictureOfDayData(true, asteroidRepository.getPictureOfDay())
        }
    }

    fun updateAsteroidListForFilter(filter: AsteroidSelectionFilter) {
        dataFlow = when(filter) {
            AsteroidSelectionFilter.ALL -> asteroidRepository.getAsteroids()
            AsteroidSelectionFilter.DAY -> {
                asteroidRepository.getAsteroidsForDay(getDate(0))
            }
            AsteroidSelectionFilter.WEEK -> {
                asteroidRepository.getAsteroidsForWeek(getDate(0), getDate(7))
            }
        }
        observeAsteroidList()
    }

    private fun getDate(timeLapse: Int): String {
        val calendar = Calendar.getInstance()
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        calendar.add(Calendar.DAY_OF_YEAR, timeLapse)
        return date.format(calendar.time)
    }
}