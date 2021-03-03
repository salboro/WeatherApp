package com.example.weatherapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityDataSourceImpl
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.domain.GetCityUseCase
import java.lang.IllegalArgumentException

class DetailViewModelFactory(private val cityName: String): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            val cityDataSource = CityDataSourceImpl()
            val cityRepository = CityRepositoryImpl(cityDataSource)
            val getCityUseCase = GetCityUseCase(cityRepository, cityName)

            return DetailViewModel(getCityUseCase, cityName) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}