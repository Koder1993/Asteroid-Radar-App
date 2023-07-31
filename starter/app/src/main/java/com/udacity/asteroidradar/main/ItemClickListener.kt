package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.Asteroid

interface ItemClickListener {
    fun onItemClick(asteroid: Asteroid)
}