package com.example.weatherapp.presentation.nearCityList

import android.content.pm.PackageManager
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
import com.example.weatherapp.databinding.FragmentListNearBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class NearCityListFragment : Fragment() {


    private val REQUEST_LOCATION_PERMISSION = 1

    private lateinit var binding: FragmentListNearBinding
    private lateinit var viewModelNearCity: NearCityListViewModel
    private lateinit var viewModelFactory: NearCityListViewModelFactory
    private lateinit var locationService: FusedLocationProviderClient
    private lateinit var adapter: NearCitiesAdapter
    private lateinit var weatherAppLocationService: WeatherAppLocationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNearBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        locationService = LocationServices.getFusedLocationProviderClient(requireActivity())

        weatherAppLocationService = WeatherAppLocationService(requireContext(), requireActivity())

        viewModelFactory = NearCityListViewModelFactory(dataSource, weatherAppLocationService)
        viewModelNearCity =
            ViewModelProvider(this, viewModelFactory).get(NearCityListViewModel::class.java)

        adapter = NearCitiesAdapter(::onCityClicked)
        binding.citiesList.adapter = adapter

        viewModelNearCity.setLocation()

        viewModelNearCity.location.observe(viewLifecycleOwner, Observer {
            viewModelNearCity.getCities()
        })

        viewModelNearCity.cityList.observe(viewLifecycleOwner, Observer { cities ->
            bindCitiesList(cities)
        })

        refreshApp()

        return binding.root
    }

    private fun refreshApp() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModelNearCity.setLocation()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun bindCitiesList(list: List<City>) {
        adapter.data = list
        adapter.location = viewModelNearCity.location.value
    }

    private fun onCityClicked(city: City) {
        this.findNavController()
            .navigate(NearCityListFragmentDirections.actionListFragmentToDetailFragment(city))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                weatherAppLocationService.enablePermission()
            }
        }
    }
}