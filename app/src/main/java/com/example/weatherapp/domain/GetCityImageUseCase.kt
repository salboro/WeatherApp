package com.example.weatherapp.domain

class GetCityImageUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(cityName: String): String {
        return cityRepository.getCityImage(cityName)
    }
}