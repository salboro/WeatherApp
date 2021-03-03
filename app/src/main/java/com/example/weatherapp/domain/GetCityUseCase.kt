package com.example.weatherapp.domain

class GetCityUseCase(private val cityRepository: CityRepository, private val cityName: String) {

    operator fun invoke(cityName: String): City? {
        return cityRepository.getCity(cityName)
    }
}