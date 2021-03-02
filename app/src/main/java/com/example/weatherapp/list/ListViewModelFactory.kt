package com.example.weatherapp.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.repositories.CityRepository
import java.lang.IllegalArgumentException

class ListViewModelFactory(private val cityRepository: CityRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(cityRepository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}