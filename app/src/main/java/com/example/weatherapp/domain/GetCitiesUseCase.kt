package com.example.weatherapp.domain

import com.example.weatherapp.data.network.City

class GetCitiesUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(latitude: Double, longitude: Double): List<City> {
        return cityRepository.getCities(latitude, longitude)
    }
}