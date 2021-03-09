package com.example.weatherapp.domain

import com.example.weatherapp.data.network.City

class GetCityByNameUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(cityName: String): City? {
        return cityRepository.getCityByName(cityName)
    }
}