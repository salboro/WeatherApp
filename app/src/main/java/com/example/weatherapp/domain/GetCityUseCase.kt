package com.example.weatherapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.CityWeather

class GetCityUseCase(private val cityRepository: CityRepository, private val cityId: Long) {

    suspend operator fun invoke(cityId: Long): CityWeather? {
        return cityRepository.getCity(cityId)
    }
}