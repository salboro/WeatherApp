package com.example.weatherapp.presentation.favoriteCityList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentListFavoriteBinding

class FavoriteCityListFragment : Fragment() {

    private lateinit var binding: FragmentListFavoriteBinding
    private lateinit var viewModel: FavoriteCityListViewModel
    private lateinit var viewModelFactory: FavoriteCityListViewModelFactory
    private lateinit var adapter: FavoriteCitiesAdapter
    private lateinit var weatherAppLocationService: WeatherAppLocationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListFavoriteBinding.inflate(inflater, container, false)

        weatherAppLocationService = WeatherAppLocationService(requireContext(), requireActivity())

        binding.progressBar.visibility = View.VISIBLE
        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        viewModelFactory = FavoriteCityListViewModelFactory(dataSource, weatherAppLocationService)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoriteCityListViewModel::class.java)
        adapter = FavoriteCitiesAdapter(::onCityClicked)
        binding.favoriteCitiesList.adapter = adapter

        viewModel.cityList.observe(viewLifecycleOwner, Observer {
            bindCitiesList(it)
        })

        return binding.root
    }


    private fun bindCitiesList(list: List<City>) {
        binding.progressBar.visibility = View.GONE
        adapter.data = list
    }

    private fun onCityClicked(city: City) {
        this.findNavController()
            .navigate(
                FavoriteCityListFragmentDirections.actionFavoriteCityListFragmentToDetailFragment(
                    city
                )
            )
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshCities()
    }
}