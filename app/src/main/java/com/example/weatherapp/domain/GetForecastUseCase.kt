package com.example.weatherapp.domain

import com.example.weatherapp.data.network.ForecastList

class GetForecastUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(cityId: Long): ForecastList? {
        return cityRepository.getForecast(cityId)
    }
}