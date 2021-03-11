package com.example.weatherapp.presentation.favoriteCityList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.domain.GetFavoriteCitiesFromDBUseCase
import com.example.weatherapp.domain.GetFavoriteCitiesUseCase

class FavoriteCityListViewModelFactory(
    private val dataSource: WeatherAppDatabaseDao,
    private val weatherAppLocationService: WeatherAppLocationService
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteCityListViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource, weatherAppLocationService)
            val getFavoriteCitiesUseCase = GetFavoriteCitiesUseCase(cityRepository)
            val getFavoriteCitiesFromDBUseCase = GetFavoriteCitiesFromDBUseCase(cityRepository)
            return FavoriteCityListViewModel(
                getFavoriteCitiesUseCase,
                getFavoriteCitiesFromDBUseCase
            ) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}