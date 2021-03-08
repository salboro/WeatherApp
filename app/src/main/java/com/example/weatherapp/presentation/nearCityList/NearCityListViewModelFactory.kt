package com.example.weatherapp.presentation.nearCityList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.domain.GetCitiesUseCase

class NearCityListViewModelFactory(
    private val dataSource: WeatherAppDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NearCityListViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource)
            val getCitiesFromApiUseCase = GetCitiesUseCase(cityRepository)
            return NearCityListViewModel(getCitiesFromApiUseCase) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}