package com.example.weatherapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.WeatherApiService
import com.example.weatherapp.domain.GetCityUseCase
import java.lang.IllegalArgumentException
import javax.sql.DataSource

class DetailViewModelFactory(
    private val city: City
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(city) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}