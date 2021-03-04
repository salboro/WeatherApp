package com.example.weatherapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.GetCityUseCase

class DetailViewModel(getCityUseCase: GetCityUseCase, cityName: String): ViewModel() {

    private val _city = MutableLiveData<City>()

    val city: LiveData<City>
        get() = _city

    init {
        _city.value = getCityUseCase(cityName)
    }
}