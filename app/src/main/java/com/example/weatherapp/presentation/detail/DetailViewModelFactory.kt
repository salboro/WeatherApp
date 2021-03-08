package com.example.weatherapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.DeleteFavoriteCityUseCase
import com.example.weatherapp.domain.GetFavoriteCityUseCase
import com.example.weatherapp.domain.SetFavoriteCityUseCase

class DetailViewModelFactory(
    private val dataSource: WeatherAppDatabaseDao,
    private val city: City
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource)
            val getFavoriteCityUseCase = GetFavoriteCityUseCase(cityRepository)
            val setFavoriteCityUseCase = SetFavoriteCityUseCase(cityRepository)
            val deleteFavoriteCityUseCase = DeleteFavoriteCityUseCase(cityRepository)
            return DetailViewModel(
                city,
                getFavoriteCityUseCase,
                setFavoriteCityUseCase,
                deleteFavoriteCityUseCase
            ) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}