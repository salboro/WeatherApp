package com.example.weatherapp.presentation.nearCityList

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.data.WeatherAppLocationService
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentListNearBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class NearCityListFragment : Fragment() {


    private val REQUEST_LOCATION_PERMISSION = 1

    private lateinit var binding: FragmentListNearBinding
    private lateinit var viewModelNearCity: NearCityListViewModel
    private lateinit var viewModelFactory: NearCityListViewModelFactory
    private lateinit var locationService: FusedLocationProviderClient
    private lateinit var adapter: CitiesAdapter
    private lateinit var weatherAppLocationService: WeatherAppLocationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNearBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        locationService = LocationServices.getFusedLocationProviderClient(requireActivity())
        viewModelFactory = NearCityListViewModelFactory(dataSource)
        viewModelNearCity =
            ViewModelProvider(this, viewModelFactory).get(NearCityListViewModel::class.java)
        adapter = CitiesAdapter(::onCityClicked)

        weatherAppLocationService =
            WeatherAppLocationService(requireContext(), requireActivity()) { location ->
                viewModelNearCity.fetchLocation(location)
                adapter.fetchLocation(location)
                binding.citiesList.adapter = adapter
                viewModelNearCity.cityList.observe(viewLifecycleOwner, Observer { cities ->
                    bindCitiesList(cities)
                })
            }

        lifecycleScope.launch {
            weatherAppLocationService.getLastLocation()
        }

        refreshApp()

        return binding.root
    }

    private fun refreshApp() {
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                weatherAppLocationService.requestNewLocation()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun bindCitiesList(list: List<City>) {
        adapter.data = list
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