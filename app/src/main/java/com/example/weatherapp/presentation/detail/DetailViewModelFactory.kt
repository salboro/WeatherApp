package com.example.weatherapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.domain.GetCityUseCase
import java.lang.IllegalArgumentException
import javax.sql.DataSource

class DetailViewModelFactory(
    private val dataSource: WeatherAppDatabaseDao,
    private val cityId: Long): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource)
            val getCityUseCase = GetCityUseCase(cityRepository, cityId)

            return DetailViewModel(getCityUseCase, cityId) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}