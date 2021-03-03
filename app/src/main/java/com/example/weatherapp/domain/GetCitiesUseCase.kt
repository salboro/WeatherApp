package com.example.weatherapp.domain

class GetCitiesUseCase(private val cityRepository: CityRepository) {

    operator fun invoke(): List<City> {
        return cityRepository.getCities()
    }
}