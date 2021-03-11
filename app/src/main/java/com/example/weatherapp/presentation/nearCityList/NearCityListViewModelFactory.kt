package com.example.weatherapp.presentation.nearCityList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.domain.GetCitiesUseCase
import com.example.weatherapp.domain.GetLocationUseCase

class NearCityListViewModelFactory(
    private val dataSource: WeatherAppDatabaseDao,
    private val weatherAppLocationService: WeatherAppLocationService
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NearCityListViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource, weatherAppLocationService)
            val getCitiesFromApiUseCase = GetCitiesUseCase(cityRepository)
            val getLocationUseCase = GetLocationUseCase(cityRepository)
            return NearCityListViewModel(getCitiesFromApiUseCase, getLocationUseCase) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}