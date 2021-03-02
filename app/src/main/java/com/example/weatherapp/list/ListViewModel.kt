package com.example.weatherapp.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.City
import com.example.weatherapp.repositories.CityRepository

class ListViewModel(private val cityRepository: CityRepository): ViewModel() {

    val cityList = MutableLiveData<List<City>>()

    fun getCities() {
        cityList.value = cityRepository.getCities()
    }

}