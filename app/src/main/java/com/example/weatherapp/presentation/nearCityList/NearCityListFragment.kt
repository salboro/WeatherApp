package com.example.weatherapp.presentation.nearCityList

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.network.City
import com.example.weatherapp.databinding.FragmentListNearBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class NearCityListFragment : Fragment() {

    private val REQUEST_LOCATION_PERMISSION = 1

    private var isPermissionChecked: Boolean = false

    private lateinit var binding: FragmentListNearBinding
    private lateinit var viewModelNearCity: NearCityListViewModel
    private lateinit var viewModelFactory: NearCityListViewModelFactory
    private lateinit var locationService: FusedLocationProviderClient
    private lateinit var adapter: CitiesAdapter
    private lateinit var locationRequest: LocationRequest

    private lateinit var locationCallback: LocationCallback

    private var currentLocation: Location? = null

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


        lifecycleScope.launch {
            getLastLocation()
        }

        refreshApp()

        return binding.root
    }

    private fun refreshApp() {
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                requestNewLocation()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private suspend fun getLastLocation() {
        checkPermission()
        if (isPermissionChecked) {
            if (isLocationEnable()) {
                locationService.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModelNearCity.fetchLocation(location)
                        adapter.fetchLocation(location)
                        binding.citiesList.adapter = adapter
                        viewModelNearCity.cityList.observe(viewLifecycleOwner, Observer { cities ->
                            bindCitiesList(cities)
                        })
                    } else {
                        requestNewLocation()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "You have trouble with connection.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun requestNewLocation() {
        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                checkPermission()
                locationService.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        viewModelNearCity.fetchLocation(it)
                        adapter.fetchLocation(it)
                        binding.citiesList.adapter = adapter
                        viewModelNearCity.cityList.observe(viewLifecycleOwner, Observer {
                            bindCitiesList(it)
                        })
                    } else {
                        requestNewLocation()
                    }
                }
            }
        }

        checkPermission()
        locationService.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    private fun bindCitiesList(list: List<City>) {
        adapter.data = list
    }

    private fun onCityClicked(city: City) {
        this.findNavController()
            .navigate(NearCityListFragmentDirections.actionListFragmentToDetailFragment(city))
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            enablePermission()
        } else {
            isPermissionChecked = true
        }
    }

    private fun isLocationEnable(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || (locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ))
    }

    private fun isPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }


    private fun enablePermission() {
        if (isPermissionGranted()) {
            isPermissionChecked = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enablePermission()
            }
        }
    }
}