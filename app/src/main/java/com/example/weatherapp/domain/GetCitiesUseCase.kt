package com.example.weatherapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.CityWeather

class GetCitiesUseCase(private val cityRepository: CityRepository) {

    operator fun invoke(): LiveData<List<CityWeather>> {
        return cityRepository.getCities()
    }
}