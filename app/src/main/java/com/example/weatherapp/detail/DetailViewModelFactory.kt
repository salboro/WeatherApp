package com.example.weatherapp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.list.ListViewModel
import com.example.weatherapp.repositories.CityRepository
import java.lang.IllegalArgumentException

class DetailViewModelFactory(private val cityRepository: CityRepository, private val cityName: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(cityRepository, cityName) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}