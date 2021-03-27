package com.example.weatherapp.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetForecastUseCase

class ForecastViewModelFactory(
    private val dataSource: WeatherAppDatabaseDao,
    private val city: City,
    private val weatherLocationServices: WeatherAppLocationService
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource, weatherLocationServices)
            val getForecastUseCase = GetForecastUseCase(cityRepository)
            return ForecastViewModel(
                city,
                getForecastUseCase
            ) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }

}