package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidViewHolder(private val itemAsteroidBinding: ItemAsteroidBinding) :
    RecyclerView.ViewHolder(itemAsteroidBinding.root) {

    fun bind(asteroid: Asteroid, clickListener: ItemClickListener) {
        itemAsteroidBinding.asteroid = asteroid
        itemAsteroidBinding.clickListener = clickListener
        itemAsteroidBinding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): AsteroidViewHolder {
            return AsteroidViewHolder(
                ItemAsteroidBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}