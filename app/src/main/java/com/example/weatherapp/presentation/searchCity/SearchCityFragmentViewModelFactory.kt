package com.example.weatherapp.presentation.searchCity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.domain.GetCityByNameUseCase

class SearchCityFragmentViewModelFactory(
    private val dataSource: WeatherAppDatabaseDao,
    private val context: Context,
    private val weatherAppLocationService: WeatherAppLocationService
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityFragmentViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource, weatherAppLocationService)
            val getCityByNameUseCase = GetCityByNameUseCase(cityRepository)
            return SearchCityFragmentViewModel(getCityByNameUseCase, context) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}