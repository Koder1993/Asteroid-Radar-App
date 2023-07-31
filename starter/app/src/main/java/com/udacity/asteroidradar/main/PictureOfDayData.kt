package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.PictureOfDay

data class PictureOfDayData(
    var isValid: Boolean = false,
    var pictureOfDay: PictureOfDay?,
)