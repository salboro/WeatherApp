package com.example.weatherapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.City
import com.example.weatherapp.repositories.CityRepository

class DetailViewModel(cityRepository: CityRepository, cityName: String): ViewModel() {

    val city = MutableLiveData<City>()

    init {
        city.value = cityRepository.getCity(cityName)
    }
}