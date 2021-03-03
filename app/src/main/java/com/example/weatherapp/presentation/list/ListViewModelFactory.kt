package com.example.weatherapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.CityDataSource
import com.example.weatherapp.data.CityDataSourceImpl
import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.GetCitiesUseCase
import java.lang.IllegalArgumentException

class ListViewModelFactory : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            val cityDataSource = CityDataSourceImpl()
            val cityRepository = CityRepositoryImpl(cityDataSource)
            val getCitiesUseCase = GetCitiesUseCase(cityRepository)
            return ListViewModel(getCitiesUseCase) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}