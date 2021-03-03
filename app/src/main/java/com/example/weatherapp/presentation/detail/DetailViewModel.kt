package com.example.weatherapp.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.GetCityUseCase

class DetailViewModel(getCityUseCase: GetCityUseCase, cityName: String): ViewModel() {

    val city = MutableLiveData<City>()

    init {
        city.value = getCityUseCase(cityName)
    }
}