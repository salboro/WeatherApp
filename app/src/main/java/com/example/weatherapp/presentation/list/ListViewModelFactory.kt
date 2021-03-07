package com.example.weatherapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.network.WeatherApiService
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.GetCitiesFromApiUseCase
import com.example.weatherapp.domain.GetCitiesUseCase
import java.lang.IllegalArgumentException
import javax.sql.DataSource

class ListViewModelFactory(private val dataSource: WeatherAppDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            val cityRepository = CityRepositoryImpl(dataSource)
            val getCitiesFromApiUseCase = GetCitiesFromApiUseCase(cityRepository)
            return ListViewModel(getCitiesFromApiUseCase) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}