package com.example.weatherapp.domain

import com.example.weatherapp.data.network.City

class GetCitiesFromApiUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(): List<City> {
        return cityRepository.getCitiesFromApi()
    }
}