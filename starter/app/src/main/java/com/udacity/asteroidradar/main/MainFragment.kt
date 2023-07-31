package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), ItemClickListener {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var asteroidListAdapter: AsteroidListAdapter

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        asteroidListAdapter = AsteroidListAdapter(this)
        binding.asteroidRecycler.adapter = asteroidListAdapter

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.asteroidUiState.collect {
                    asteroidListAdapter.submitList(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pictureOfDayUiState.collect { data ->
                    val pictureOfDay = data.pictureOfDay
                    pictureOfDay?.let {
                        Picasso.with(requireContext()).load(it.url)
                            .into(binding.activityMainImageOfTheDay)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.show_week_asteroids -> AsteroidSelectionFilter.WEEK
            R.id.show_today_asteroids -> AsteroidSelectionFilter.DAY
            R.id.show_all_asteroids -> AsteroidSelectionFilter.ALL
            else -> AsteroidSelectionFilter.ALL
        }
        viewModel.updateAsteroidListForFilter(filter)
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(asteroid: Asteroid) {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
    }
}
